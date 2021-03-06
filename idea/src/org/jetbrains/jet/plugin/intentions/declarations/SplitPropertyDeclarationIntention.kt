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

package org.jetbrains.jet.plugin.intentions.declarations

import com.intellij.openapi.editor.Editor
import org.jetbrains.jet.lang.psi.JetProperty
import org.jetbrains.jet.plugin.intentions.JetSelfTargetingIntention

public class SplitPropertyDeclarationIntention : JetSelfTargetingIntention<JetProperty>("split.property.declaration", javaClass()) {
    override fun isApplicableTo(element: JetProperty): Boolean = DeclarationUtils.checkSplitProperty(element)

    override fun applyTo(element: JetProperty, editor: Editor) {
        DeclarationUtils.splitPropertyDeclaration(element)
    }
}
