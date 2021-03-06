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

package org.jetbrains.jet.plugin.refactoring;

import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import kotlin.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.lang.descriptors.DeclarationDescriptor;
import org.jetbrains.jet.lang.psi.JetElement;
import org.jetbrains.jet.lang.psi.JetExpression;
import org.jetbrains.jet.lang.psi.JetVisitorVoid;
import org.jetbrains.jet.lang.resolve.BindingContext;
import org.jetbrains.jet.plugin.codeInsight.TipsManager;
import org.jetbrains.jet.plugin.project.AnalyzerFacadeWithCache;

import java.util.Collection;

public class JetNameValidatorImpl extends JetNameValidator {
    private final PsiElement myContainer;
    private final PsiElement myAnchor;
    private final Function1<DeclarationDescriptor, Boolean> myFilter;

    public JetNameValidatorImpl(PsiElement container, PsiElement anchor, Function1<DeclarationDescriptor, Boolean> filter) {
        super(container.getProject());
        myContainer = container;
        myAnchor = anchor;
        myFilter = filter;
    }

    @Override
    protected boolean validateInner(String name) {
        PsiElement sibling;
        if (myAnchor != null) {
            sibling = myAnchor;
        }
        else {
            if (myContainer instanceof JetExpression) {
                return checkElement(name, myContainer);
            }
            sibling = myContainer.getFirstChild();
        }

        while (sibling != null) {
            if (!checkElement(name, sibling)) return false;
            sibling = sibling.getNextSibling();
        }

        return true;
    }

    private boolean checkElement(final String name, PsiElement sibling) {
        if (!(sibling instanceof JetElement)) return true;

        final BindingContext bindingContext  = AnalyzerFacadeWithCache.getContextForElement((JetElement) sibling);

        final Ref<Boolean> result = new Ref<Boolean>(true);
        JetVisitorVoid visitor = new JetVisitorVoid() {
            @Override
            public void visitElement(PsiElement element) {
                if (result.get()) {
                    element.acceptChildren(this);
                }
            }

            @Override
            public void visitExpression(@NotNull JetExpression expression) {
                Collection<DeclarationDescriptor> variants = TipsManager.getVariantsNoReceiver(expression, bindingContext);
                for (DeclarationDescriptor variant : variants) {
                    if (variant.getName().asString().equals(name) && myFilter.invoke(variant)) {
                        result.set(false);
                        return;
                    }
                }
                super.visitExpression(expression);
            }
        };
        sibling.accept(visitor);
        return result.get();
    }
}
