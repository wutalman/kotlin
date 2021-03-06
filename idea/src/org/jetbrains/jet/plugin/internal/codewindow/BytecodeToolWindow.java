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

package org.jetbrains.jet.plugin.internal.codewindow;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.Alarm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.OutputFile;
import org.jetbrains.jet.OutputFileCollection;
import org.jetbrains.jet.analyzer.AnalyzeExhaust;
import org.jetbrains.jet.codegen.ClassBuilderFactories;
import org.jetbrains.jet.codegen.CompilationErrorHandler;
import org.jetbrains.jet.codegen.KotlinCodegenFacade;
import org.jetbrains.jet.codegen.state.GenerationState;
import org.jetbrains.jet.codegen.state.Progress;
import org.jetbrains.jet.lang.psi.JetFile;
import org.jetbrains.jet.plugin.JetPluginUtil;
import org.jetbrains.jet.plugin.caches.resolve.ResolvePackage;
import org.jetbrains.jet.plugin.internal.Location;
import org.jetbrains.jet.plugin.util.InfinitePeriodicalTask;
import org.jetbrains.jet.plugin.util.LongRunningReadTask;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BytecodeToolWindow extends JPanel implements Disposable {
    private static final int UPDATE_DELAY = 1000;
    private static final String DEFAULT_TEXT = "/*\n" +
                                               "Generated bytecode for Kotlin source file.\n" +
                                               "No Kotlin source file is opened.\n" +
                                               "*/";

    public class UpdateBytecodeToolWindowTask extends LongRunningReadTask<Location, String> {
        @Override
        protected Location prepareRequestInfo() {
            if (!toolWindow.isVisible()) {
                return null;
            }

            Location location = Location.fromEditor(FileEditorManager.getInstance(myProject).getSelectedTextEditor(), myProject);
            if (location.getEditor() == null) {
                return null;
            }

            JetFile file = location.getJetFile();
            if (file == null || !JetPluginUtil.isInSource(file, false)) {
                return null;
            }

            return location;
        }

        @NotNull
        @Override
        protected Location cloneRequestInfo(@NotNull Location location) {
            Location newLocation = super.cloneRequestInfo(location);
            assert location.equals(newLocation) : "cloneRequestInfo should generate same location object";
            return newLocation;
        }

        @Override
        protected void hideResultOnInvalidLocation() {
            setText(DEFAULT_TEXT);
        }

        @NotNull
        @Override
        protected String processRequest(@NotNull Location location) {
            JetFile jetFile = location.getJetFile();
            assert jetFile != null;

            GenerationState state;
            try {
                AnalyzeExhaust exhaust = ResolvePackage.getAnalysisResults(jetFile);
                if (exhaust.isError()) {
                    return printStackTraceToString(exhaust.getError());
                }
                state = new GenerationState(jetFile.getProject(), ClassBuilderFactories.TEST, Progress.DEAF, exhaust.getBindingContext(),
                                            Collections.singletonList(jetFile), true, true,
                                            GenerationState.GenerateClassFilter.GENERATE_ALL,
                                            enableInline.isSelected());
                KotlinCodegenFacade.compileCorrectFiles(state, CompilationErrorHandler.THROW_EXCEPTION);
            }
            catch (ProcessCanceledException e) {
                throw e;
            }
            catch (Exception e) {
                return printStackTraceToString(e);
            }

            StringBuilder answer = new StringBuilder();

            OutputFileCollection outputFiles = state.getFactory();
            for (OutputFile outputFile : outputFiles.asList()) {
                answer.append("// ================");
                answer.append(outputFile.getRelativePath());
                answer.append(" =================\n");
                answer.append(outputFile.asText()).append("\n\n");
            }

            return answer.toString();
        }

        @Override
        protected void onResultReady(@NotNull Location requestInfo, String resultText) {
            Editor editor = requestInfo.getEditor();
            assert editor != null;

            if (resultText == null) {
                return;
            }

            setText(resultText);

            int fileStartOffset = requestInfo.getStartOffset();
            int fileEndOffset = requestInfo.getEndOffset();

            Document document = editor.getDocument();
            int startLine = document.getLineNumber(fileStartOffset);
            int endLine = document.getLineNumber(fileEndOffset);
            if (endLine > startLine && fileEndOffset > 0 && document.getCharsSequence().charAt(fileEndOffset - 1) == '\n') {
                endLine--;
            }

            Document byteCodeDocument = myEditor.getDocument();

            Pair<Integer, Integer> linesRange = mapLines(byteCodeDocument.getText(), startLine, endLine);
            int endSelectionLineIndex = Math.min(linesRange.second + 1, byteCodeDocument.getLineCount());

            int startOffset = byteCodeDocument.getLineStartOffset(linesRange.first);
            int endOffset = Math.min(byteCodeDocument.getLineStartOffset(endSelectionLineIndex), byteCodeDocument.getTextLength());

            myEditor.getCaretModel().moveToOffset(endOffset);
            myEditor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
            myEditor.getCaretModel().moveToOffset(startOffset);
            myEditor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);

            myEditor.getSelectionModel().setSelection(startOffset, endOffset);
        }
    }

    private final Editor myEditor;
    private final Project myProject;
    private final ToolWindow toolWindow;
    private final JCheckBox enableInline;

    public BytecodeToolWindow(Project project, ToolWindow toolWindow) {
        super(new BorderLayout());
        myProject = project;
        this.toolWindow = toolWindow;

        myEditor = EditorFactory.getInstance().createEditor(
                EditorFactory.getInstance().createDocument(""), project, JavaFileType.INSTANCE, true);
        add(myEditor.getComponent());

        JPanel optionPanel = new JPanel(new BorderLayout());
        add(optionPanel, BorderLayout.NORTH);

        /*TODO: try to extract default parameter from compiler options*/
        enableInline = new JCheckBox("Enable inline");
        optionPanel.add(enableInline, BorderLayout.WEST);

        new InfinitePeriodicalTask(UPDATE_DELAY, Alarm.ThreadToUse.SWING_THREAD, this, new Computable<LongRunningReadTask>() {
            @Override
            public LongRunningReadTask compute() {
                return new UpdateBytecodeToolWindowTask();
            }
        }).start();

        setText(DEFAULT_TEXT);
    }

    private static Pair<Integer, Integer> mapLines(String text, int startLine, int endLine) {
        int byteCodeLine = 0;
        int byteCodeStartLine = -1;
        int byteCodeEndLine = -1;

        List<Integer> lines = new ArrayList<Integer>();
        for (String line : text.split("\n")) {
            line = line.trim();

            if (line.startsWith("LINENUMBER")) {
                int ktLineNum = new Scanner(line.substring("LINENUMBER".length())).nextInt() - 1;
                lines.add(ktLineNum);
            }
        }
        Collections.sort(lines);

        for (Integer line : lines) {
            if (line >= startLine) {
                startLine = line;
                break;
            }
        }

        for (String line : text.split("\n")) {
            line = line.trim();

            if (line.startsWith("LINENUMBER")) {
                int ktLineNum = new Scanner(line.substring("LINENUMBER".length())).nextInt() - 1;

                if (byteCodeStartLine < 0 && ktLineNum == startLine) {
                    byteCodeStartLine = byteCodeLine;
                }

                if (byteCodeStartLine > 0 && ktLineNum > endLine) {
                    byteCodeEndLine = byteCodeLine - 1;
                    break;
                }
            }

            if (byteCodeStartLine >= 0 && (line.startsWith("MAXSTACK") || line.startsWith("LOCALVARIABLE") || line.isEmpty())) {
                byteCodeEndLine = byteCodeLine - 1;
                break;
            }


            byteCodeLine++;
        }

        if (byteCodeStartLine == -1 || byteCodeEndLine == -1) {
            return new Pair<Integer, Integer>(0, 0);
        }
        else {
            return new Pair<Integer, Integer>(byteCodeStartLine, byteCodeEndLine);
        }
    }

    private static String printStackTraceToString(Throwable e) {
        StringWriter out = new StringWriter(1024);
        PrintWriter printWriter = new PrintWriter(out);
        try {
            e.printStackTrace(printWriter);
            return out.toString().replace("\r", "");
        }
        finally {
            printWriter.close();
        }
    }

    private void setText(@NotNull final String resultText) {
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                myEditor.getDocument().setText(StringUtil.convertLineSeparators(resultText));
            }
        });
    }

    @Override
    public void dispose() {
        EditorFactory.getInstance().releaseEditor(myEditor);
    }
}