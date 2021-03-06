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

package org.jetbrains.jet.completion;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.completion.AbstractMultiFileJvmBasicCompletionTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/completion/basic/multifile")
@InnerTestClasses({})
public class MultiFileJvmBasicCompletionTestGenerated extends AbstractMultiFileJvmBasicCompletionTest {
    public void testAllFilesPresentInMultifile() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("idea/testData/completion/basic/multifile"), Pattern.compile("^([^\\.]+)\\.kt$"), true);
    }
    
    @TestMetadata("CompleteImportedFunction.kt")
    public void testCompleteImportedFunction() throws Exception {
        doTest("idea/testData/completion/basic/multifile/CompleteImportedFunction.kt");
    }
    
    @TestMetadata("CompletionOnImportedFunction.kt")
    public void testCompletionOnImportedFunction() throws Exception {
        doTest("idea/testData/completion/basic/multifile/CompletionOnImportedFunction.kt");
    }
    
    @TestMetadata("DoNotCompleteWithConstraints.kt")
    public void testDoNotCompleteWithConstraints() throws Exception {
        doTest("idea/testData/completion/basic/multifile/DoNotCompleteWithConstraints.kt");
    }
    
    @TestMetadata("ExtensionFunction.kt")
    public void testExtensionFunction() throws Exception {
        doTest("idea/testData/completion/basic/multifile/ExtensionFunction.kt");
    }
    
    @TestMetadata("ExtensionFunctionOnImportedFunction.kt")
    public void testExtensionFunctionOnImportedFunction() throws Exception {
        doTest("idea/testData/completion/basic/multifile/ExtensionFunctionOnImportedFunction.kt");
    }
    
    @TestMetadata("ExtensionOnNullable.kt")
    public void testExtensionOnNullable() throws Exception {
        doTest("idea/testData/completion/basic/multifile/ExtensionOnNullable.kt");
    }
    
    @TestMetadata("InImportedFunctionLiteralParameter.kt")
    public void testInImportedFunctionLiteralParameter() throws Exception {
        doTest("idea/testData/completion/basic/multifile/InImportedFunctionLiteralParameter.kt");
    }
    
    @TestMetadata("JavaInnerClasses.kt")
    public void testJavaInnerClasses() throws Exception {
        doTest("idea/testData/completion/basic/multifile/JavaInnerClasses.kt");
    }
    
    @TestMetadata("NotImportedExtensionFunction.kt")
    public void testNotImportedExtensionFunction() throws Exception {
        doTest("idea/testData/completion/basic/multifile/NotImportedExtensionFunction.kt");
    }
    
    @TestMetadata("NotImportedJavaClass.kt")
    public void testNotImportedJavaClass() throws Exception {
        doTest("idea/testData/completion/basic/multifile/NotImportedJavaClass.kt");
    }
    
    @TestMetadata("NotImportedObject.kt")
    public void testNotImportedObject() throws Exception {
        doTest("idea/testData/completion/basic/multifile/NotImportedObject.kt");
    }
    
    @TestMetadata("TopLevelFunction.kt")
    public void testTopLevelFunction() throws Exception {
        doTest("idea/testData/completion/basic/multifile/TopLevelFunction.kt");
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("MultiFileJvmBasicCompletionTestGenerated");
        suite.addTestSuite(MultiFileJvmBasicCompletionTestGenerated.class);
        return suite;
    }
}
