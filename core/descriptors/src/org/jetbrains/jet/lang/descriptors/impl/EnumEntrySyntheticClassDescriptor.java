/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.lang.descriptors.impl;

import kotlin.Function0;
import kotlin.Function1;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.lang.descriptors.*;
import org.jetbrains.jet.lang.descriptors.annotations.Annotations;
import org.jetbrains.jet.lang.resolve.DescriptorFactory;
import org.jetbrains.jet.lang.resolve.OverridingUtil;
import org.jetbrains.jet.lang.resolve.name.Name;
import org.jetbrains.jet.lang.resolve.name.SpecialNames;
import org.jetbrains.jet.lang.resolve.scopes.JetScope;
import org.jetbrains.jet.lang.resolve.scopes.JetScopeImpl;
import org.jetbrains.jet.lang.types.JetType;
import org.jetbrains.jet.lang.types.TypeConstructor;
import org.jetbrains.jet.lang.types.TypeConstructorImpl;
import org.jetbrains.jet.storage.MemoizedFunctionToNotNull;
import org.jetbrains.jet.storage.NotNullLazyValue;
import org.jetbrains.jet.storage.StorageManager;
import org.jetbrains.jet.utils.Printer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EnumEntrySyntheticClassDescriptor extends ClassDescriptorBase {
    private final ClassKind kind;
    private final TypeConstructor typeConstructor;
    private final ConstructorDescriptor primaryConstructor;
    private final JetScope scope;
    private final EnumEntrySyntheticClassDescriptor classObjectDescriptor;
    private final NotNullLazyValue<Collection<Name>> enumMemberNames;

    /**
     * Creates and initializes descriptors for enum entry with the given name and its class object
     * @param enumMemberNames needed for fake overrides resolution
     */
    @NotNull
    public static EnumEntrySyntheticClassDescriptor create(
            @NotNull StorageManager storageManager,
            @NotNull ClassDescriptor enumClass,
            @NotNull Name name,
            @NotNull NotNullLazyValue<Collection<Name>> enumMemberNames
    ) {
        JetType enumType = enumClass.getDefaultType();

        return new EnumEntrySyntheticClassDescriptor(storageManager, enumClass, enumType, name, ClassKind.ENUM_ENTRY, enumMemberNames);
    }

    private EnumEntrySyntheticClassDescriptor(
            @NotNull StorageManager storageManager,
            @NotNull ClassDescriptor containingClass,
            @NotNull JetType supertype,
            @NotNull Name name,
            @NotNull ClassKind kind,
            @NotNull NotNullLazyValue<Collection<Name>> enumMemberNames
    ) {
        super(storageManager, containingClass, name);
        this.kind = kind;

        this.typeConstructor =
                new TypeConstructorImpl(this, getAnnotations(), true, "enum entry", Collections.<TypeParameterDescriptor>emptyList(),
                                        Collections.singleton(supertype));

        this.scope = new EnumEntryScope(storageManager);
        this.enumMemberNames = enumMemberNames;

        ConstructorDescriptorImpl primaryConstructor = DescriptorFactory.createPrimaryConstructorForObject(this);
        primaryConstructor.setReturnType(getDefaultType());
        this.primaryConstructor = primaryConstructor;

        this.classObjectDescriptor =
                kind == ClassKind.CLASS_OBJECT
                ? null
                : new EnumEntrySyntheticClassDescriptor(storageManager, this, getDefaultType(), SpecialNames.getClassObjectName(name),
                                                        ClassKind.CLASS_OBJECT, enumMemberNames);
    }

    @NotNull
    @Override
    protected JetScope getScopeForMemberLookup() {
        return scope;
    }

    @NotNull
    @Override
    public Collection<ConstructorDescriptor> getConstructors() {
        return Collections.singleton(primaryConstructor);
    }

    @NotNull
    @Override
    public TypeConstructor getTypeConstructor() {
        return typeConstructor;
    }

    @Nullable
    @Override
    public ClassDescriptor getClassObjectDescriptor() {
        return classObjectDescriptor;
    }

    @NotNull
    @Override
    public ClassKind getKind() {
        return kind;
    }

    @NotNull
    @Override
    public Modality getModality() {
        return Modality.FINAL;
    }

    @NotNull
    @Override
    public Visibility getVisibility() {
        return Visibilities.PUBLIC;
    }

    @Override
    public boolean isInner() {
        return false;
    }

    @Nullable
    @Override
    public ConstructorDescriptor getUnsubstitutedPrimaryConstructor() {
        return primaryConstructor;
    }

    @NotNull
    @Override
    public Annotations getAnnotations() {
        // TODO
        return Annotations.EMPTY;
    }

    private class EnumEntryScope extends JetScopeImpl {
        private final MemoizedFunctionToNotNull<Name, Collection<FunctionDescriptor>> functions;
        private final MemoizedFunctionToNotNull<Name, Collection<PropertyDescriptor>> properties;
        private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;

        public EnumEntryScope(@NotNull StorageManager storageManager) {
            this.functions = storageManager.createMemoizedFunction(new Function1<Name, Collection<FunctionDescriptor>>() {
                @Override
                public Collection<FunctionDescriptor> invoke(Name name) {
                    return computeFunctions(name);
                }
            });
            this.properties = storageManager.createMemoizedFunction(new Function1<Name, Collection<PropertyDescriptor>>() {
                @Override
                public Collection<PropertyDescriptor> invoke(Name name) {
                    return computeProperties(name);
                }
            });
            this.allDescriptors = storageManager.createLazyValue(new Function0<Collection<DeclarationDescriptor>>() {
                @Override
                public Collection<DeclarationDescriptor> invoke() {
                    return computeAllDeclarations();
                }
            });
        }

        @NotNull
        @Override
        @SuppressWarnings("unchecked")
        public Collection<VariableDescriptor> getProperties(@NotNull Name name) {
            return (Collection) properties.invoke(name);
        }

        @NotNull
        @SuppressWarnings("unchecked")
        private Collection<PropertyDescriptor> computeProperties(@NotNull Name name) {
            return resolveFakeOverrides(name, (Collection) getSupertypeScope().getProperties(name));
        }

        @NotNull
        @Override
        public Collection<FunctionDescriptor> getFunctions(@NotNull Name name) {
            return functions.invoke(name);
        }

        @NotNull
        private Collection<FunctionDescriptor> computeFunctions(@NotNull Name name) {
            return resolveFakeOverrides(name, getSupertypeScope().getFunctions(name));
        }

        @NotNull
        private JetScope getSupertypeScope() {
            Collection<JetType> supertype = getTypeConstructor().getSupertypes();
            assert supertype.size() == 1 : "Enum entry and its class object both should have exactly one supertype: " + supertype;
            return supertype.iterator().next().getMemberScope();
        }

        @NotNull
        private <D extends CallableMemberDescriptor> Collection<D> resolveFakeOverrides(
                @NotNull Name name,
                @NotNull Collection<D> fromSupertypes
        ) {
            final Set<D> result = new HashSet<D>();

            OverridingUtil.generateOverridesInFunctionGroup(
                    name, fromSupertypes, Collections.<D>emptySet(), EnumEntrySyntheticClassDescriptor.this,
                    new OverridingUtil.DescriptorSink() {
                        @Override
                        @SuppressWarnings("unchecked")
                        public void addToScope(@NotNull CallableMemberDescriptor fakeOverride) {
                            OverridingUtil.resolveUnknownVisibilityForMember(fakeOverride, new Function1<CallableMemberDescriptor, Unit>() {
                                @Override
                                public Unit invoke(@NotNull CallableMemberDescriptor descriptor) {
                                    // Do nothing
                                    return Unit.VALUE;
                                }
                            });
                            result.add((D) fakeOverride);
                        }

                        @Override
                        public void conflict(@NotNull CallableMemberDescriptor fromSuper, @NotNull CallableMemberDescriptor fromCurrent) {
                            // Do nothing
                        }
                    }
            );

            return result;
        }

        @NotNull
        @Override
        public DeclarationDescriptor getContainingDeclaration() {
            return EnumEntrySyntheticClassDescriptor.this;
        }

        @NotNull
        @Override
        public Collection<DeclarationDescriptor> getAllDescriptors() {
            return allDescriptors.invoke();
        }

        @NotNull
        private Collection<DeclarationDescriptor> computeAllDeclarations() {
            Collection<DeclarationDescriptor> result = new HashSet<DeclarationDescriptor>();
            for (Name name : enumMemberNames.invoke()) {
                result.addAll(getFunctions(name));
                result.addAll(getProperties(name));
            }
            return result;
        }

        @Override
        public void printScopeStructure(@NotNull Printer p) {
            p.println("enum entry scope for " + EnumEntrySyntheticClassDescriptor.this);
        }
    }
}