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

package org.jetbrains.jet.lang.resolve.java.resolver

import org.jetbrains.jet.lang.descriptors.*
import org.jetbrains.jet.lang.resolve.java.structure.JavaClass
import org.jetbrains.jet.lang.resolve.java.structure.JavaElement
import org.jetbrains.jet.lang.resolve.java.structure.JavaField
import org.jetbrains.jet.lang.resolve.java.structure.JavaMethod
import org.jetbrains.jet.lang.resolve.lazy.ResolveSession
import org.jetbrains.jet.lang.resolve.lazy.ResolveSessionUtils
import org.jetbrains.jet.lang.resolve.name.FqName
import org.jetbrains.jet.lang.types.TypeProjection
import javax.inject.Inject
import java.util.Collections
import kotlin.properties.Delegates
import org.jetbrains.jet.lang.types.lang.KotlinBuiltIns
import com.intellij.openapi.application.ApplicationManager
import org.jetbrains.jet.rt.annotation.AssertInvisibleInResolver
import com.intellij.openapi.diagnostic.Logger

public class LazyResolveBasedCache() : JavaResolverCache {
    private var resolveSession by Delegates.notNull<ResolveSession>()

    class object {
        private val ASSERT_INVISIBLE_IN_RESOLVER_ANNOTATION = DescriptorResolverUtils.fqNameByClass(javaClass<AssertInvisibleInResolver>())
        private val LOG = Logger.getInstance(javaClass<TraceBasedJavaResolverCache>())
    }


    Inject
    public fun setSession(resolveSession: ResolveSession) {
        this.resolveSession = resolveSession
    }

    override fun getClassResolvedFromSource(fqName: FqName): ClassDescriptor? {
        val classes = ResolveSessionUtils.getClassDescriptorsByFqName(resolveSession, fqName)
        return if (classes.isNotEmpty()) classes.first() else null
    }

    override fun getMethod(method: JavaMethod): SimpleFunctionDescriptor? {
        val classFqName = method.getContainingClass().getFqName()
        if (classFqName == null) return null

        val containingClass = getClassResolvedFromSource(classFqName)
        if (containingClass == null) return null

        val memberScope = containingClass.getMemberScope(Collections.emptyList<TypeProjection>())
        val functions = memberScope.getFunctions(method.getName())

        return if (functions.isNotEmpty()) (functions.first() as SimpleFunctionDescriptor) else null
    }

    override fun getConstructor(constructor: JavaElement): ConstructorDescriptor? = null

    override fun getClass(javaClass: JavaClass): ClassDescriptor? {
        val fqName = javaClass.getFqName()
        if (fqName != null && KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME == fqName.parent()) {
            if (javaClass.findAnnotation(ASSERT_INVISIBLE_IN_RESOLVER_ANNOTATION) != null) {
                if (ApplicationManager.getApplication()!!.isInternal()) {
                    LOG.error("Classpath is configured incorrectly:" + " class " + fqName + " from runtime must not be loaded by compiler")
                }
            }
        }

        return null
    }

    override fun recordMethod(method: JavaMethod, descriptor: SimpleFunctionDescriptor) {
    }

    override fun recordConstructor(element: JavaElement, descriptor: ConstructorDescriptor) {
    }

    override fun recordField(field: JavaField, descriptor: PropertyDescriptor) {
    }

    override fun recordClass(javaClass: JavaClass, descriptor: ClassDescriptor) {
    }
}
