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

package org.jetbrains.jet.codegen;

import com.google.common.collect.Lists;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.jet.OutputFile;
import org.jetbrains.jet.OutputFileCollection;
import org.jetbrains.jet.codegen.state.GenerationState;
import org.jetbrains.jet.codegen.state.GenerationStateAware;
import org.jetbrains.jet.lang.descriptors.ClassDescriptor;
import org.jetbrains.jet.lang.psi.JetFile;
import org.jetbrains.jet.lang.resolve.name.FqName;

import javax.inject.Inject;
import java.io.File;
import java.util.*;

import static org.jetbrains.jet.codegen.AsmUtil.asmTypeByFqNameWithoutInnerClasses;
import static org.jetbrains.jet.codegen.AsmUtil.isPrimitive;
import static org.jetbrains.jet.lang.resolve.java.PackageClassUtils.getPackageClassFqName;

public final class ClassFileFactory extends GenerationStateAware implements OutputFileCollection {
    @NotNull private ClassBuilderFactory builderFactory;

    private final Map<FqName, PackageCodegen> package2codegen = new HashMap<FqName, PackageCodegen>();
    private final Map<String, ClassBuilderAndSourceFileList> generators = new LinkedHashMap<String, ClassBuilderAndSourceFileList>();
    private boolean isDone = false;

    public ClassFileFactory(@NotNull GenerationState state) {
        super(state);
    }

    @Inject
    public void setBuilderFactory(@NotNull ClassBuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
    }

    @NotNull
    ClassBuilder newVisitor(@NotNull Type asmType, @NotNull PsiFile sourceFile) {
        return newVisitor(asmType, Collections.singletonList(sourceFile));
    }

    @NotNull
    private ClassBuilder newVisitor(@NotNull Type asmType, @NotNull Collection<? extends PsiFile> sourceFiles) {
        String outputFilePath = asmType.getInternalName() + ".class";
        state.getProgress().reportOutput(toIoFilesIgnoringNonPhysical(sourceFiles), new File(outputFilePath));
        ClassBuilder answer = builderFactory.newClassBuilder();
        generators.put(outputFilePath, new ClassBuilderAndSourceFileList(answer, sourceFiles));
        return answer;
    }

    void done() {
        if (!isDone) {
            isDone = true;
            for (PackageCodegen codegen : package2codegen.values()) {
                codegen.done();
            }
        }
    }

    @NotNull
    @Override
    public List<OutputFile> asList() {
        done();
        return ContainerUtil.map(generators.keySet(), new Function<String, OutputFile>() {
            @Override
            public OutputFile fun(String relativeClassFilePath) {
                return new OutputClassFile(relativeClassFilePath);
            }
        });
    }

    @Override
    @Nullable
    public OutputFile get(@NotNull String relativePath) {
        if (generators.containsKey(relativePath)) return new OutputClassFile(relativePath);

        return null;
    }

    public String createText() {
        StringBuilder answer = new StringBuilder();

        for (OutputFile file : asList()) {
            answer.append("@").append(file.getRelativePath()).append('\n');
            answer.append(file.asText());
        }

        return answer.toString();
    }

    public PackageCodegen forPackage(final FqName fqName, final Collection<JetFile> files) {
        assert !isDone : "Already done!";
        PackageCodegen codegen = package2codegen.get(fqName);
        if (codegen == null) {
            ClassBuilderOnDemand onDemand = new ClassBuilderOnDemand() {
                @NotNull
                @Override
                protected ClassBuilder createClassBuilder() {
                    return newVisitor(asmTypeByFqNameWithoutInnerClasses(getPackageClassFqName(fqName)), files);
                }
            };
            codegen = new PackageCodegen(onDemand, fqName, state, files);
            package2codegen.put(fqName, codegen);
        }

        return codegen;
    }

    public ClassBuilder forClassImplementation(ClassDescriptor aClass, PsiFile sourceFile) {
        Type type = state.getTypeMapper().mapClass(aClass);
        if (isPrimitive(type)) {
            throw new IllegalStateException("Codegen for primitive type is not possible: " + aClass);
        }
        return newVisitor(type, sourceFile);
    }

    public ClassBuilder forLambdaInlining(Type lambaType, PsiFile sourceFile) {
        if (isPrimitive(lambaType)) {
            throw new IllegalStateException("Codegen for primitive type is not possible: " + lambaType);
        }
        return newVisitor(lambaType, sourceFile);
    }

    @NotNull
    public ClassBuilder forPackagePart(@NotNull Type asmType, @NotNull PsiFile sourceFile) {
        return newVisitor(asmType, sourceFile);
    }

    @NotNull
    public ClassBuilder forTraitImplementation(@NotNull ClassDescriptor aClass, @NotNull GenerationState state, @NotNull PsiFile file) {
        return newVisitor(state.getTypeMapper().mapTraitImpl(aClass), file);
    }

    private static Collection<File> toIoFilesIgnoringNonPhysical(Collection<? extends PsiFile> psiFiles) {
        List<File> result = Lists.newArrayList();
        for (PsiFile psiFile : psiFiles) {
            VirtualFile virtualFile = psiFile.getVirtualFile();
            // We ignore non-physical files here, because this code is needed to tell the make what inputs affect which outputs
            // a non-physical file cannot be processed by make
            if (virtualFile != null) {
                result.add(new File(virtualFile.getPath()));
            }
        }
        return result;
    }

    private final class OutputClassFile implements OutputFile {
        final String relativeClassFilePath;

        OutputClassFile(String relativeClassFilePath) {
            this.relativeClassFilePath = relativeClassFilePath;
        }

        @NotNull
        @Override
        public String getRelativePath() {
            return relativeClassFilePath;
        }

        @NotNull
        @Override
        public List<File> getSourceFiles() {
            ClassBuilderAndSourceFileList pair = generators.get(relativeClassFilePath);
            if (pair == null) {
                throw new IllegalStateException("No record for binary file " + relativeClassFilePath);
            }

            return ContainerUtil.mapNotNull(
                    pair.sourceFiles,
                    new Function<PsiFile, File>() {
                        @Override
                        public File fun(PsiFile file) {
                            VirtualFile virtualFile = file.getVirtualFile();
                            if (virtualFile == null) return null;

                            return VfsUtilCore.virtualToIoFile(virtualFile);
                        }
                    }
            );
        }

        @NotNull
        @Override
        public byte[] asByteArray() {
            return builderFactory.asBytes(generators.get(relativeClassFilePath).classBuilder);
        }

        @NotNull
        @Override
        public String asText() {
            return builderFactory.asText(generators.get(relativeClassFilePath).classBuilder);
        }

        @NotNull
        @Override
        public String toString() {
            return getRelativePath() + " (compiled from " + getSourceFiles() + ")";
        }
    }

    private static final class ClassBuilderAndSourceFileList {
        private final ClassBuilder classBuilder;
        private final Collection<? extends PsiFile> sourceFiles;

        private ClassBuilderAndSourceFileList(ClassBuilder classBuilder, Collection<? extends PsiFile> sourceFiles) {
            this.classBuilder = classBuilder;
            this.sourceFiles = sourceFiles;
        }
    }

    public void removeInlinedClasses(Set<String> classNamesToRemove) {
        for (String classInternalName : classNamesToRemove) {
            generators.remove(classInternalName + ".class");
        }
    }
}
