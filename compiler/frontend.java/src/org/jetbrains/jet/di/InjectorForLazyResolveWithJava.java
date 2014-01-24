/*
 * Copyright 2010-2014 JetBrains s.r.o.
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

package org.jetbrains.jet.di;

import com.intellij.openapi.project.Project;
import org.jetbrains.jet.lang.resolve.lazy.declarations.FileBasedDeclarationProviderFactory.FileBaseDeclarationConfiguration;
import org.jetbrains.jet.storage.LazyResolveStorageManager;
import org.jetbrains.jet.lang.resolve.lazy.ResolveSession;
import org.jetbrains.jet.lang.resolve.java.JavaDescriptorResolver;
import org.jetbrains.jet.lang.resolve.BindingTraceContext;
import org.jetbrains.jet.lang.resolve.calls.CallResolverExtensionProvider;
import org.jetbrains.jet.lang.resolve.java.JavaClassFinderImpl;
import org.jetbrains.jet.lang.resolve.java.resolver.TraceBasedExternalSignatureResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.LazyResolveBasedCache;
import org.jetbrains.jet.lang.resolve.java.resolver.TraceBasedErrorReporter;
import org.jetbrains.jet.lang.resolve.java.resolver.PsiBasedMethodSignatureChecker;
import org.jetbrains.jet.lang.resolve.java.resolver.PsiBasedExternalAnnotationResolver;
import org.jetbrains.jet.lang.PlatformToKotlinClassMap;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaPackageFragmentProviderImpl;
import org.jetbrains.jet.lang.resolve.kotlin.VirtualFileFinder;
import org.jetbrains.jet.lang.descriptors.ModuleDescriptorImpl;
import org.jetbrains.jet.lang.resolve.AnnotationResolver;
import org.jetbrains.jet.lang.resolve.calls.CallResolver;
import org.jetbrains.jet.lang.resolve.calls.ArgumentTypeResolver;
import org.jetbrains.jet.lang.types.expressions.ExpressionTypingServices;
import org.jetbrains.jet.lang.resolve.calls.CallExpressionResolver;
import org.jetbrains.jet.lang.resolve.DescriptorResolver;
import org.jetbrains.jet.lang.resolve.DelegatedPropertyResolver;
import org.jetbrains.jet.lang.resolve.TypeResolver;
import org.jetbrains.jet.lang.resolve.QualifiedExpressionResolver;
import org.jetbrains.jet.lang.resolve.calls.CandidateResolver;
import org.jetbrains.jet.lang.psi.JetImportsFactory;
import org.jetbrains.jet.lang.resolve.lazy.ScopeProvider;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaClassResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaAnnotationResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaAnnotationArgumentResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaTypeTransformer;
import org.jetbrains.jet.lang.resolve.kotlin.DeserializedDescriptorResolver;
import org.jetbrains.jet.lang.resolve.kotlin.AnnotationDescriptorDeserializer;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaFunctionResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaTypeParameterResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaValueParameterResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaMemberResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaConstructorResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaPropertyResolver;
import org.jetbrains.jet.lang.resolve.java.resolver.JavaSupertypeResolver;
import org.jetbrains.annotations.NotNull;
import javax.annotation.PreDestroy;

/* This file is generated by org.jetbrains.jet.generators.injectors.GenerateInjectors. DO NOT EDIT! */
@SuppressWarnings("ALL")
public class InjectorForLazyResolveWithJava {
    
    private final Project project;
    private final FileBaseDeclarationConfiguration fileBaseDeclarationConfiguration;
    private final LazyResolveStorageManager lazyResolveStorageManager;
    private final ResolveSession resolveSession;
    private final JavaDescriptorResolver javaDescriptorResolver;
    private final BindingTraceContext bindingTraceContext;
    private final FileBasedDeclarationConfigurationWithJava fileBasedDeclarationConfigurationWithJava;
    private final CallResolverExtensionProvider callResolverExtensionProvider;
    private final JavaClassFinderImpl javaClassFinder;
    private final TraceBasedExternalSignatureResolver traceBasedExternalSignatureResolver;
    private final LazyResolveBasedCache lazyResolveBasedCache;
    private final TraceBasedErrorReporter traceBasedErrorReporter;
    private final PsiBasedMethodSignatureChecker psiBasedMethodSignatureChecker;
    private final PsiBasedExternalAnnotationResolver psiBasedExternalAnnotationResolver;
    private final PlatformToKotlinClassMap platformToKotlinClassMap;
    private final JavaPackageFragmentProviderImpl javaPackageFragmentProvider;
    private final VirtualFileFinder virtualFileFinder;
    private final ModuleDescriptorImpl module;
    private final AnnotationResolver annotationResolver;
    private final CallResolver callResolver;
    private final ArgumentTypeResolver argumentTypeResolver;
    private final ExpressionTypingServices expressionTypingServices;
    private final CallExpressionResolver callExpressionResolver;
    private final DescriptorResolver descriptorResolver;
    private final DelegatedPropertyResolver delegatedPropertyResolver;
    private final TypeResolver typeResolver;
    private final QualifiedExpressionResolver qualifiedExpressionResolver;
    private final CandidateResolver candidateResolver;
    private final JetImportsFactory jetImportsFactory;
    private final ScopeProvider scopeProvider;
    private final JavaClassResolver javaClassResolver;
    private final JavaAnnotationResolver javaAnnotationResolver;
    private final JavaAnnotationArgumentResolver javaAnnotationArgumentResolver;
    private final JavaTypeTransformer javaTypeTransformer;
    private final DeserializedDescriptorResolver deserializedDescriptorResolver;
    private final AnnotationDescriptorDeserializer annotationDescriptorDeserializer;
    private final JavaFunctionResolver javaFunctionResolver;
    private final JavaTypeParameterResolver javaTypeParameterResolver;
    private final JavaValueParameterResolver javaValueParameterResolver;
    private final JavaMemberResolver javaMemberResolver;
    private final JavaConstructorResolver javaConstructorResolver;
    private final JavaPropertyResolver javaPropertyResolver;
    private final JavaSupertypeResolver javaSupertypeResolver;
    
    public InjectorForLazyResolveWithJava(
        @NotNull Project project,
        @NotNull FileBaseDeclarationConfiguration fileBaseDeclarationConfiguration,
        @NotNull LazyResolveStorageManager lazyResolveStorageManager
    ) {
        this.project = project;
        this.fileBaseDeclarationConfiguration = fileBaseDeclarationConfiguration;
        this.lazyResolveStorageManager = lazyResolveStorageManager;
        this.module = org.jetbrains.jet.lang.resolve.java.AnalyzerFacadeForJVM.createJavaModule("<fake-jdr-module>");
        this.javaClassFinder = new JavaClassFinderImpl();
        this.fileBasedDeclarationConfigurationWithJava = new FileBasedDeclarationConfigurationWithJava(lazyResolveStorageManager, javaClassFinder, fileBaseDeclarationConfiguration);
        this.bindingTraceContext = new BindingTraceContext();
        this.resolveSession = new ResolveSession(lazyResolveStorageManager, getModule(), fileBasedDeclarationConfigurationWithJava, bindingTraceContext);
        this.javaDescriptorResolver = new JavaDescriptorResolver();
        this.callResolverExtensionProvider = new CallResolverExtensionProvider();
        this.traceBasedExternalSignatureResolver = new TraceBasedExternalSignatureResolver();
        this.lazyResolveBasedCache = new LazyResolveBasedCache();
        this.traceBasedErrorReporter = new TraceBasedErrorReporter();
        this.psiBasedMethodSignatureChecker = new PsiBasedMethodSignatureChecker();
        this.psiBasedExternalAnnotationResolver = new PsiBasedExternalAnnotationResolver();
        this.platformToKotlinClassMap = module.getPlatformToKotlinClassMap();
        this.javaPackageFragmentProvider = new JavaPackageFragmentProviderImpl();
        this.virtualFileFinder = org.jetbrains.jet.lang.resolve.kotlin.VirtualFileFinder.SERVICE.getInstance(project);
        this.annotationResolver = new AnnotationResolver();
        this.callResolver = new CallResolver();
        this.argumentTypeResolver = new ArgumentTypeResolver();
        this.expressionTypingServices = new ExpressionTypingServices();
        this.callExpressionResolver = new CallExpressionResolver();
        this.descriptorResolver = new DescriptorResolver();
        this.delegatedPropertyResolver = new DelegatedPropertyResolver();
        this.typeResolver = new TypeResolver();
        this.qualifiedExpressionResolver = new QualifiedExpressionResolver();
        this.candidateResolver = new CandidateResolver();
        this.jetImportsFactory = new JetImportsFactory();
        this.scopeProvider = new ScopeProvider(getResolveSession());
        this.javaClassResolver = new JavaClassResolver();
        this.javaAnnotationResolver = new JavaAnnotationResolver();
        this.javaAnnotationArgumentResolver = new JavaAnnotationArgumentResolver();
        this.javaTypeTransformer = new JavaTypeTransformer();
        this.deserializedDescriptorResolver = new DeserializedDescriptorResolver();
        this.annotationDescriptorDeserializer = new AnnotationDescriptorDeserializer(lazyResolveStorageManager);
        this.javaFunctionResolver = new JavaFunctionResolver();
        this.javaTypeParameterResolver = new JavaTypeParameterResolver();
        this.javaValueParameterResolver = new JavaValueParameterResolver();
        this.javaMemberResolver = new JavaMemberResolver();
        this.javaConstructorResolver = new JavaConstructorResolver();
        this.javaPropertyResolver = new JavaPropertyResolver();
        this.javaSupertypeResolver = new JavaSupertypeResolver();

        this.resolveSession.setAnnotationResolve(annotationResolver);
        this.resolveSession.setDescriptorResolver(descriptorResolver);
        this.resolveSession.setJetImportFactory(jetImportsFactory);
        this.resolveSession.setQualifiedExpressionResolver(qualifiedExpressionResolver);
        this.resolveSession.setScopeProvider(scopeProvider);
        this.resolveSession.setTypeResolver(typeResolver);

        this.javaDescriptorResolver.setClassResolver(javaClassResolver);
        this.javaDescriptorResolver.setDeserializedDescriptorResolver(deserializedDescriptorResolver);
        this.javaDescriptorResolver.setErrorReporter(traceBasedErrorReporter);
        this.javaDescriptorResolver.setExternalAnnotationResolver(psiBasedExternalAnnotationResolver);
        this.javaDescriptorResolver.setExternalSignatureResolver(traceBasedExternalSignatureResolver);
        this.javaDescriptorResolver.setJavaClassFinder(javaClassFinder);
        this.javaDescriptorResolver.setJavaResolverCache(lazyResolveBasedCache);
        this.javaDescriptorResolver.setKotlinClassFinder(virtualFileFinder);
        this.javaDescriptorResolver.setModule(module);
        this.javaDescriptorResolver.setPackageFragmentProvider(javaPackageFragmentProvider);
        this.javaDescriptorResolver.setSignatureChecker(psiBasedMethodSignatureChecker);
        this.javaDescriptorResolver.setStorageManager(lazyResolveStorageManager);

        javaClassFinder.setProject(project);

        traceBasedExternalSignatureResolver.setAnnotationResolver(javaAnnotationResolver);
        traceBasedExternalSignatureResolver.setTrace(bindingTraceContext);

        lazyResolveBasedCache.setSession(resolveSession);

        traceBasedErrorReporter.setTrace(bindingTraceContext);

        psiBasedMethodSignatureChecker.setAnnotationResolver(javaAnnotationResolver);
        psiBasedMethodSignatureChecker.setExternalSignatureResolver(traceBasedExternalSignatureResolver);

        javaPackageFragmentProvider.setCache(lazyResolveBasedCache);
        javaPackageFragmentProvider.setDeserializedDescriptorResolver(deserializedDescriptorResolver);
        javaPackageFragmentProvider.setJavaClassFinder(javaClassFinder);
        javaPackageFragmentProvider.setJavaDescriptorResolver(javaDescriptorResolver);
        javaPackageFragmentProvider.setKotlinClassFinder(virtualFileFinder);
        javaPackageFragmentProvider.setMemberResolver(javaMemberResolver);
        javaPackageFragmentProvider.setModule(module);

        annotationResolver.setCallResolver(callResolver);
        annotationResolver.setExpressionTypingServices(expressionTypingServices);

        callResolver.setArgumentTypeResolver(argumentTypeResolver);
        callResolver.setCandidateResolver(candidateResolver);
        callResolver.setExpressionTypingServices(expressionTypingServices);
        callResolver.setTypeResolver(typeResolver);

        argumentTypeResolver.setExpressionTypingServices(expressionTypingServices);
        argumentTypeResolver.setTypeResolver(typeResolver);

        expressionTypingServices.setAnnotationResolver(annotationResolver);
        expressionTypingServices.setCallExpressionResolver(callExpressionResolver);
        expressionTypingServices.setCallResolver(callResolver);
        expressionTypingServices.setDescriptorResolver(descriptorResolver);
        expressionTypingServices.setExtensionProvider(callResolverExtensionProvider);
        expressionTypingServices.setPlatformToKotlinClassMap(platformToKotlinClassMap);
        expressionTypingServices.setProject(project);
        expressionTypingServices.setTypeResolver(typeResolver);

        callExpressionResolver.setExpressionTypingServices(expressionTypingServices);

        descriptorResolver.setAnnotationResolver(annotationResolver);
        descriptorResolver.setDelegatedPropertyResolver(delegatedPropertyResolver);
        descriptorResolver.setExpressionTypingServices(expressionTypingServices);
        descriptorResolver.setTypeResolver(typeResolver);

        delegatedPropertyResolver.setExpressionTypingServices(expressionTypingServices);

        typeResolver.setAnnotationResolver(annotationResolver);
        typeResolver.setModuleDescriptor(module);
        typeResolver.setQualifiedExpressionResolver(qualifiedExpressionResolver);

        candidateResolver.setArgumentTypeResolver(argumentTypeResolver);

        jetImportsFactory.setProject(project);

        javaClassResolver.setAnnotationResolver(javaAnnotationResolver);
        javaClassResolver.setCache(lazyResolveBasedCache);
        javaClassResolver.setDeserializedDescriptorResolver(deserializedDescriptorResolver);
        javaClassResolver.setFunctionResolver(javaFunctionResolver);
        javaClassResolver.setJavaClassFinder(javaClassFinder);
        javaClassResolver.setKotlinClassFinder(virtualFileFinder);
        javaClassResolver.setMemberResolver(javaMemberResolver);
        javaClassResolver.setPackageFragmentProvider(javaPackageFragmentProvider);
        javaClassResolver.setSupertypesResolver(javaSupertypeResolver);
        javaClassResolver.setTypeParameterResolver(javaTypeParameterResolver);

        javaAnnotationResolver.setArgumentResolver(javaAnnotationArgumentResolver);
        javaAnnotationResolver.setClassResolver(javaClassResolver);
        javaAnnotationResolver.setExternalAnnotationResolver(psiBasedExternalAnnotationResolver);

        javaAnnotationArgumentResolver.setAnnotationResolver(javaAnnotationResolver);
        javaAnnotationArgumentResolver.setClassResolver(javaClassResolver);
        javaAnnotationArgumentResolver.setTypeTransformer(javaTypeTransformer);

        javaTypeTransformer.setClassResolver(javaClassResolver);

        deserializedDescriptorResolver.setAnnotationDeserializer(annotationDescriptorDeserializer);
        deserializedDescriptorResolver.setErrorReporter(traceBasedErrorReporter);
        deserializedDescriptorResolver.setJavaDescriptorResolver(javaDescriptorResolver);
        deserializedDescriptorResolver.setJavaPackageFragmentProvider(javaPackageFragmentProvider);
        deserializedDescriptorResolver.setStorageManager(lazyResolveStorageManager);

        annotationDescriptorDeserializer.setErrorReporter(traceBasedErrorReporter);
        annotationDescriptorDeserializer.setJavaDescriptorResolver(javaDescriptorResolver);
        annotationDescriptorDeserializer.setKotlinClassFinder(virtualFileFinder);

        javaFunctionResolver.setAnnotationResolver(javaAnnotationResolver);
        javaFunctionResolver.setCache(lazyResolveBasedCache);
        javaFunctionResolver.setErrorReporter(traceBasedErrorReporter);
        javaFunctionResolver.setExternalSignatureResolver(traceBasedExternalSignatureResolver);
        javaFunctionResolver.setSignatureChecker(psiBasedMethodSignatureChecker);
        javaFunctionResolver.setTypeParameterResolver(javaTypeParameterResolver);
        javaFunctionResolver.setTypeTransformer(javaTypeTransformer);
        javaFunctionResolver.setValueParameterResolver(javaValueParameterResolver);

        javaTypeParameterResolver.setTypeTransformer(javaTypeTransformer);

        javaValueParameterResolver.setAnnotationResolver(javaAnnotationResolver);
        javaValueParameterResolver.setTypeTransformer(javaTypeTransformer);

        javaMemberResolver.setClassResolver(javaClassResolver);
        javaMemberResolver.setConstructorResolver(javaConstructorResolver);
        javaMemberResolver.setFunctionResolver(javaFunctionResolver);
        javaMemberResolver.setPropertyResolver(javaPropertyResolver);

        javaConstructorResolver.setCache(lazyResolveBasedCache);
        javaConstructorResolver.setExternalSignatureResolver(traceBasedExternalSignatureResolver);
        javaConstructorResolver.setTypeTransformer(javaTypeTransformer);
        javaConstructorResolver.setValueParameterResolver(javaValueParameterResolver);

        javaPropertyResolver.setAnnotationResolver(javaAnnotationResolver);
        javaPropertyResolver.setCache(lazyResolveBasedCache);
        javaPropertyResolver.setErrorReporter(traceBasedErrorReporter);
        javaPropertyResolver.setExternalSignatureResolver(traceBasedExternalSignatureResolver);
        javaPropertyResolver.setTypeTransformer(javaTypeTransformer);

        javaSupertypeResolver.setClassResolver(javaClassResolver);
        javaSupertypeResolver.setTypeTransformer(javaTypeTransformer);

        javaClassFinder.initialize();

    }
    
    @PreDestroy
    public void destroy() {
    }
    
    public ResolveSession getResolveSession() {
        return this.resolveSession;
    }
    
    public JavaDescriptorResolver getJavaDescriptorResolver() {
        return this.javaDescriptorResolver;
    }
    
    public ModuleDescriptorImpl getModule() {
        return this.module;
    }
    
}
