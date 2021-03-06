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

package org.jetbrains.jet.plugin.refactoring.extractFunction

import com.intellij.psi.PsiElement
import org.jetbrains.jet.lang.descriptors.DeclarationDescriptor
import org.jetbrains.jet.lang.resolve.calls.model.ResolvedCall
import org.jetbrains.jet.lang.psi.JetFile
import com.intellij.openapi.project.Project
import org.jetbrains.jet.lang.psi.JetExpression
import com.intellij.openapi.util.TextRange
import kotlin.properties.Delegates
import java.util.HashMap
import org.jetbrains.jet.plugin.codeInsight.JetFileReferencesResolver
import org.jetbrains.jet.lang.psi.JetSimpleNameExpression
import org.jetbrains.jet.lang.psi.JetThisExpression
import org.jetbrains.jet.lang.resolve.BindingContext
import org.jetbrains.jet.plugin.codeInsight.DescriptorToDeclarationUtil
import java.util.Collections
import org.jetbrains.jet.lang.psi.JetBlockExpression
import org.jetbrains.jet.renderer.DescriptorRenderer
import org.jetbrains.jet.lang.psi.psiUtil.getParentByTypeAndBranch
import org.jetbrains.jet.lang.psi.JetQualifiedExpression
import org.jetbrains.jet.lang.psi.psiUtil.isInsideOf
import java.util.ArrayList
import com.intellij.psi.PsiNamedElement
import org.jetbrains.jet.lang.psi.JetSuperExpression
import org.jetbrains.jet.lang.types.JetType
import org.jetbrains.jet.plugin.project.AnalyzerFacadeWithCache

data class ResolveResult(
        val originalRefExpr: JetSimpleNameExpression,
        val declaration: PsiNamedElement,
        val descriptor: DeclarationDescriptor,
        val resolvedCall: ResolvedCall<*>?
)

data class ResolvedReferenceInfo(
        val refExpr: JetSimpleNameExpression,
        val offsetInBody: Int,
        val resolveResult: ResolveResult
)

class ExtractionData(
        val originalFile: JetFile,
        val originalElements: List<PsiElement>,
        val nextSibling: PsiElement
) {
    val project: Project = originalFile.getProject()

    fun getExpressions(): List<JetExpression> = originalElements.filterIsInstance(javaClass<JetExpression>())

    fun getCodeFragmentTextRange(): TextRange? {
        val originalElements = originalElements
        return when (originalElements.size) {
            0 -> null
            1 -> originalElements.first!!.getTextRange()
            else -> {
                val from = originalElements.first!!.getTextRange()!!.getStartOffset()
                val to = originalElements.last!!.getTextRange()!!.getEndOffset()
                TextRange(from, to)
            }
        }
    }

    fun getCodeFragmentText(): String =
            getCodeFragmentTextRange()?.let { originalFile.getText()!!.substring(it.getStartOffset(), it.getEndOffset()) } ?: ""

    val originalStartOffset = originalElements.first?.let { e -> e.getTextRange()!!.getStartOffset() }

    val refOffsetToDeclaration by Delegates.lazy {
        if (originalStartOffset != null) {
            val resultMap = HashMap<Int, ResolveResult>()
            for ((ref, context) in JetFileReferencesResolver.resolve(originalFile, getExpressions())) {
                if (ref !is JetSimpleNameExpression) continue

                val resolvedCallKey = (ref.getParent() as? JetThisExpression) ?: ref
                val resolvedCall = context[BindingContext.RESOLVED_CALL, resolvedCallKey]

                val descriptor = context[BindingContext.REFERENCE_TARGET, ref]
                if (descriptor == null) continue

                val declaration = DescriptorToDeclarationUtil.getDeclaration(project, descriptor, context) as? PsiNamedElement
                if (declaration == null) continue

                val offset = ref.getTextRange()!!.getStartOffset() - originalStartOffset
                resultMap[offset] = ResolveResult(ref, declaration, descriptor, resolvedCall)
            }
            resultMap
        }
        else Collections.emptyMap<Int, ResolveResult>()
    }

    fun getBrokenReferencesInfo(body: JetBlockExpression): List<ResolvedReferenceInfo> {
        val startOffset = body.getStatements().first!!.getTextRange()!!.getStartOffset()

        val referencesInfo = ArrayList<ResolvedReferenceInfo>()
        for ((ref, context) in JetFileReferencesResolver.resolve(body)) {
            if (ref !is JetSimpleNameExpression) continue

            val parent = ref.getParent()
            if (parent is JetQualifiedExpression
                    && parent.getSelectorExpression() == ref
                    && parent.getReceiverExpression() !is JetSuperExpression) continue

            val offset = ref.getTextRange()!!.getStartOffset() - startOffset
            refOffsetToDeclaration[offset]?.let { originalResolveResult ->
                val descriptor = context[BindingContext.REFERENCE_TARGET, ref]
                if (!compareDescriptors(originalResolveResult.descriptor, descriptor)
                        && !originalResolveResult.declaration.isInsideOf(originalElements)) {
                    referencesInfo.add(ResolvedReferenceInfo(ref, offset, originalResolveResult))
                }
            }
        }

        return referencesInfo
    }

    fun getInferredResultType(): JetType? =
            getExpressions().last?.let { AnalyzerFacadeWithCache.getContextForElement(it)[BindingContext.EXPRESSION_TYPE, it] }
}

private fun compareDescriptors(d1: DeclarationDescriptor?, d2: DeclarationDescriptor?): Boolean {
    return d1 == d2 ||
            (d1 != null && d2 != null &&
                    DescriptorRenderer.FQ_NAMES_IN_TYPES.render(d1) == DescriptorRenderer.FQ_NAMES_IN_TYPES.render(d2))
}