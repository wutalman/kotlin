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

package org.jetbrains.jet.formatter;

import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.testFramework.LightCodeInsightTestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.plugin.PluginTestCaseBase;
import org.jetbrains.jet.testing.SettingsConfigurator;

import java.io.File;

public abstract class JetTypingIndentationTestBase extends LightCodeInsightTestCase {

    public void doFileNewlineTest() {
        doNewlineTest(getBeforeFileName(), getAfterFileName());
    }

    public String getBeforeFileName() {
        return getTestName(false) + ".kt";
    }

    public String getAfterFileName() {
        return getTestName(false) + "_after.kt";
    }

    public String getInvertedAfterFileName() {
        return getTestName(false) + "_after_inv.kt";
    }

    public void doFileSettingNewLineTest() throws Exception {
        String originalFileText = FileUtil.loadFile(new File(getTestDataPath(), getBeforeFileName()), true);

        SettingsConfigurator configurator = JetFormatSettingsUtil.createConfigurator(originalFileText);

        configurator.configureSettings();
        doNewlineTest(getBeforeFileName(), getAfterFileName());

        configurator.configureInvertedSettings();
        doNewlineTest(getBeforeFileName(), getInvertedAfterFileName());

        getSettings().clearCodeStyleSettings();
    }

    private void doNewlineTest(String beforeFileName, String afterFileName) {
        configureByFile(beforeFileName);
        type('\n');
        checkResultByFile(afterFileName);
    }

    public static CodeStyleSettings getSettings() {
        return CodeStyleSettingsManager.getSettings(getProject());
    }

    @NotNull
    @Override
    protected String getTestDataPath() {
        String testRelativeDir = "indentationOnNewline";
        return new File(PluginTestCaseBase.getTestDataPathBase(), testRelativeDir).getPath() +
               File.separator;
    }
}
