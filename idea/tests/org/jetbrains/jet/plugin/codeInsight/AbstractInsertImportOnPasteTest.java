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

package org.jetbrains.jet.plugin.codeInsight;

import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.plugin.PluginTestCaseBase;

import java.io.File;

public abstract class AbstractInsertImportOnPasteTest extends LightCodeInsightFixtureTestCase {
    private static final String BASE_PATH = PluginTestCaseBase.getTestDataPathBase() + "/copyPaste/imports";
    private static final String DEFAULT_TO_FILE_TEXT = "package to\n\n<caret>";

    public void doTestCut(@SuppressWarnings("UnusedParameters") String path) {
        doTestAction(IdeActions.ACTION_CUT);
    }

    public void doTestCopy(@SuppressWarnings("UnusedParameters") String path) {
        doTestAction(IdeActions.ACTION_COPY);
    }

    private void doTestAction(@NotNull String cutOrCopy) {
        myFixture.setTestDataPath(BASE_PATH);
        String testName = getTestName(false);
        String dependencyFileName = testName + ".dependency.kt";
        if (new File(BASE_PATH + "/" + dependencyFileName).exists()) {
            myFixture.configureByFile(dependencyFileName);
        }
        myFixture.configureByFile(testName + ".kt");
        myFixture.performEditorAction(cutOrCopy);
        String toFileName = testName + ".to.kt";
        if (new File(BASE_PATH + "/" + toFileName).exists()) {
            myFixture.configureByFile(toFileName);
        }
        else {
            myFixture.configureByText(toFileName, DEFAULT_TO_FILE_TEXT);
        }
        myFixture.performEditorAction(IdeActions.ACTION_PASTE);
        myFixture.checkResultByFile(testName + ".expected.kt");
    }
}
