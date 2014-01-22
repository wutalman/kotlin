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

package org.jetbrains.jet.plugin.codeInsight

import com.intellij.psi.PsiFile
import com.intellij.psi.PsiElement
import com.intellij.codeInsight.editorActions.ReferenceData
import java.util.ArrayList
import com.intellij.openapi.editor.RangeMarker
import org.jetbrains.jet.lang.psi.JetReferenceExpression
import org.jetbrains.jet.lang.psi.JetPsiUtil
import com.intellij.openapi.editor.Editor
import com.intellij.codeInsight.editorActions.ReferenceTransferableData
import com.intellij.codeInsight.daemon.impl.CollectHighlightsUtil
import org.jetbrains.jet.lang.psi.JetNamedDeclaration
import org.jetbrains.jet.lang.psi.JetFile
import org.jetbrains.jet.plugin.quickfix.ImportInsertHelper
import org.jetbrains.jet.lang.resolve.name.FqName
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Ref
import com.intellij.openapi.project.DumbService
import com.intellij.psi.PsiDocumentManager
import com.intellij.openapi.application.ApplicationManager
import java.awt.datatransfer.Transferable
import com.intellij.codeInsight.CodeInsightSettings
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException
import com.intellij.codeInsight.editorActions.CopyPastePostProcessor
import org.jetbrains.jet.plugin.project.AnalyzerFacadeWithCache
import org.jetbrains.jet.plugin.references.JetPsiReference
import com.intellij.psi.ResolveResult
import org.jetbrains.jet.lang.psi.JetNamed

//NOTE: this class is based on CopyPasteReferenceProcessor and JavaCopyPasteReferenceProcessor
public class KotlinCopyPasteReferenceProcessor() : CopyPastePostProcessor<ReferenceTransferableData?> {

    override fun extractTransferableData(content: Transferable): ReferenceTransferableData? {
        var referenceData: ReferenceTransferableData? = null
        if (CodeInsightSettings.getInstance()!!.ADD_IMPORTS_ON_PASTE != CodeInsightSettings.NO) {
            try {
                val flavor = ReferenceData.getDataFlavor()
                if (flavor != null) {
                    referenceData = content.getTransferData(flavor) as ReferenceTransferableData
                }
            }
            catch (ignored: UnsupportedFlavorException) {
            }
            catch (ignored: IOException) {
            }
        }

        if (referenceData != null) {
            // copy to prevent changing of original by convertLineSeparators
            return referenceData!!.clone()
        }

        return null
    }

    override fun collectTransferableData(file: PsiFile, editor: Editor, startOffsets: IntArray, endOffsets: IntArray): ReferenceTransferableData? {
        val collectedData = ArrayList<ReferenceData>()
        for ((startOffset, endOffset) in zip(startOffsets, endOffsets)) {
            for (element in CollectHighlightsUtil.getElementsInRange(file, startOffset, endOffset)) {
                val referencedElement = getReferencedElement(element)
                if (referencedElement != null) {
                    val fqName = JetPsiUtil.getFQName(referencedElement)
                    if (fqName != null) {
                        collectedData.add(createReferenceData(element, startOffset, fqName))
                    }
                }
            }
        }

        if (collectedData.isEmpty()) {
            return null
        }

        return ReferenceTransferableData(collectedData.copyToArray())
    }

    private fun getReferencedElement(element: PsiElement): JetNamedDeclaration? {
        if (element !is JetReferenceExpression) {
            return null
        }
        val reference = element.getReference()
        if (reference == null) {
            return null
        }
        val referencedElement = reference.resolve()
        return referencedElement as? JetNamedDeclaration
    }

    override fun processTransferableData (
            project: Project,
            editor: Editor,
            bounds: RangeMarker, caretOffset: Int,
            indented: Ref<Boolean>,
            value: ReferenceTransferableData?
    ) {
        if (DumbService.getInstance(project)!!.isDumb()) {
            return
        }
        val document = editor.getDocument()
        val file = PsiDocumentManager.getInstance(project).getPsiFile(document)
        if (file !is JetFile) {
            return
        }

        PsiDocumentManager.getInstance(project).commitAllDocuments()
        val referenceData = value!!.getData()!!
        val refs = findReferencesToRestore(file, bounds, referenceData)
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        ApplicationManager.getApplication()!!.runWriteAction(Runnable {
            restoreReferences(referenceData, refs)
        })
    }

    fun findReferencesToRestore(file: PsiFile, bounds: RangeMarker, referenceData: Array<out ReferenceData>): Array<JetReferenceExpression?> {
        val result = arrayOfNulls<JetReferenceExpression>(referenceData.size)
        if (file !is JetFile) {
            return result
        }
        for (i in referenceData.indices) {
            val data = referenceData[i]
            val startOffset = data.startOffset + bounds.getStartOffset()
            val endOffset = data.endOffset + bounds.getStartOffset()
            val element = file.findElementAt(startOffset)

            //   if (element != null && element.getParent() is JetReferenceExpression) {
            val referenceExpression = element!!.getParent() as JetReferenceExpression
            val range = referenceExpression.getTextRange()!!
            if (range.getStartOffset() == startOffset && range.getEndOffset() == endOffset) {
                result[i] = referenceExpression
            }
            // }
        }
        return result
    }

    fun restoreReferences(referenceData: Array<out ReferenceData>, refs: Array<out JetReferenceExpression?>) {
        for (i in referenceData.indices) {
            val referenceExpression = refs[i]
            if (referenceExpression != null) {
                val reference = referenceExpression.getReference()
                val referencedExpressions = (reference as? JetPsiReference)?.multiResolve(/*this is ignored*/ true)?.map { it.getElement() }
                if (referencedExpressions == null || referencedExpressions.isEmpty()) {
                    //TODO
                    val data = referenceData[i]
                    ImportInsertHelper.addImportDirectiveIfNeeded(data.fqName, referenceExpression.getContainingFile() as JetFile)
                    continue
                }
                for (referencedExpression in referencedExpressions) {
                    println("${referencedExpression?.getText()} in ${referencedExpression?.getContainingFile()?.getName()}")
                }
            }
        }
    }

    private fun createReferenceData(
            element: PsiElement,
            startOffset: Int,
            fqName: FqName
    ): ReferenceData {
        val range = element.getTextRange()!!
        return ReferenceData(range.getStartOffset() - startOffset, range.getEndOffset() - startOffset, fqName.asString(), null)
    }
}

val ReferenceData.fqName: FqName
    get() = FqName(qClassName!!)

fun zip(first: IntArray, second: IntArray): Iterator<Pair<Int, Int>> {
    assert(first.size == second.size)
    return first.iterator().zip(second.iterator())
}