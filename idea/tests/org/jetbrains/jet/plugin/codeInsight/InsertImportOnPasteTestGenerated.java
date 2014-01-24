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

package org.jetbrains.jet.plugin.codeInsight;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.plugin.codeInsight.AbstractInsertImportOnPasteTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@InnerTestClasses({InsertImportOnPasteTestGenerated.Copy.class, InsertImportOnPasteTestGenerated.Cut.class})
public class InsertImportOnPasteTestGenerated extends AbstractInsertImportOnPasteTest {
    @TestMetadata("idea/testData/copyPaste/imports")
    public static class Copy extends AbstractInsertImportOnPasteTest {
        public void testAllFilesPresentInCopy() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("idea/testData/copyPaste/imports"), Pattern.compile("^([^\\.]+)\\.kt$"), true);
        }
        
        @TestMetadata("ClassAlreadyImported.kt")
        public void testClassAlreadyImported() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ClassAlreadyImported.kt");
        }
        
        @TestMetadata("ClassMember.kt")
        public void testClassMember() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ClassMember.kt");
        }
        
        @TestMetadata("ClassObject.kt")
        public void testClassObject() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ClassObject.kt");
        }
        
        @TestMetadata("ClassObjectFunInsideClass.kt")
        public void testClassObjectFunInsideClass() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ClassObjectFunInsideClass.kt");
        }
        
        @TestMetadata("ClassObjectInner.kt")
        public void testClassObjectInner() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ClassObjectInner.kt");
        }
        
        @TestMetadata("ClassType.kt")
        public void testClassType() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ClassType.kt");
        }
        
        @TestMetadata("Constructor.kt")
        public void testConstructor() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/Constructor.kt");
        }
        
        @TestMetadata("EnumEntries.kt")
        public void testEnumEntries() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/EnumEntries.kt");
        }
        
        @TestMetadata("ExtensionAsInfixOrOperator.kt")
        public void testExtensionAsInfixOrOperator() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ExtensionAsInfixOrOperator.kt");
        }
        
        @TestMetadata("ExtensionFunction.kt")
        public void testExtensionFunction() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ExtensionFunction.kt");
        }
        
        @TestMetadata("FullyQualified.kt")
        public void testFullyQualified() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/FullyQualified.kt");
        }
        
        @TestMetadata("Function.kt")
        public void testFunction() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/Function.kt");
        }
        
        @TestMetadata("FunctionAlreadyImported.kt")
        public void testFunctionAlreadyImported() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/FunctionAlreadyImported.kt");
        }
        
        @TestMetadata("FunctionParameter.kt")
        public void testFunctionParameter() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/FunctionParameter.kt");
        }
        
        @TestMetadata("ImportDependency.kt")
        public void testImportDependency() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ImportDependency.kt");
        }
        
        @TestMetadata("ImportableEntityInExtensionLiteral.kt")
        public void testImportableEntityInExtensionLiteral() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ImportableEntityInExtensionLiteral.kt");
        }
        
        @TestMetadata("ImportedElementCopied.kt")
        public void testImportedElementCopied() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ImportedElementCopied.kt");
        }
        
        @TestMetadata("Inner.kt")
        public void testInner() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/Inner.kt");
        }
        
        @TestMetadata("Local.kt")
        public void testLocal() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/Local.kt");
        }
        
        @TestMetadata("NoImportForBuiltIns.kt")
        public void testNoImportForBuiltIns() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/NoImportForBuiltIns.kt");
        }
        
        @TestMetadata("NoImportForSamePackage.kt")
        public void testNoImportForSamePackage() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/NoImportForSamePackage.kt");
        }
        
        @TestMetadata("Object.kt")
        public void testObject() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/Object.kt");
        }
        
        @TestMetadata("ReferencedElementAlsoCopied.kt")
        public void testReferencedElementAlsoCopied() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/ReferencedElementAlsoCopied.kt");
        }
        
        @TestMetadata("TopLevelProperty.kt")
        public void testTopLevelProperty() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/TopLevelProperty.kt");
        }
        
        @TestMetadata("Trait.kt")
        public void testTrait() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/Trait.kt");
        }
        
        @TestMetadata("TypeParameter.kt")
        public void testTypeParameter() throws Exception {
            doTestCopy("idea/testData/copyPaste/imports/TypeParameter.kt");
        }
        
    }
    
    @TestMetadata("idea/testData/copyPaste/imports")
    public static class Cut extends AbstractInsertImportOnPasteTest {
        public void testAllFilesPresentInCut() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("idea/testData/copyPaste/imports"), Pattern.compile("^([^\\.]+)\\.kt$"), true);
        }
        
        @TestMetadata("ClassAlreadyImported.kt")
        public void testClassAlreadyImported() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ClassAlreadyImported.kt");
        }
        
        @TestMetadata("ClassMember.kt")
        public void testClassMember() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ClassMember.kt");
        }
        
        @TestMetadata("ClassObject.kt")
        public void testClassObject() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ClassObject.kt");
        }
        
        @TestMetadata("ClassObjectFunInsideClass.kt")
        public void testClassObjectFunInsideClass() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ClassObjectFunInsideClass.kt");
        }
        
        @TestMetadata("ClassObjectInner.kt")
        public void testClassObjectInner() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ClassObjectInner.kt");
        }
        
        @TestMetadata("ClassType.kt")
        public void testClassType() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ClassType.kt");
        }
        
        @TestMetadata("Constructor.kt")
        public void testConstructor() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/Constructor.kt");
        }
        
        @TestMetadata("EnumEntries.kt")
        public void testEnumEntries() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/EnumEntries.kt");
        }
        
        @TestMetadata("ExtensionAsInfixOrOperator.kt")
        public void testExtensionAsInfixOrOperator() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ExtensionAsInfixOrOperator.kt");
        }
        
        @TestMetadata("ExtensionFunction.kt")
        public void testExtensionFunction() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ExtensionFunction.kt");
        }
        
        @TestMetadata("FullyQualified.kt")
        public void testFullyQualified() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/FullyQualified.kt");
        }
        
        @TestMetadata("Function.kt")
        public void testFunction() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/Function.kt");
        }
        
        @TestMetadata("FunctionAlreadyImported.kt")
        public void testFunctionAlreadyImported() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/FunctionAlreadyImported.kt");
        }
        
        @TestMetadata("FunctionParameter.kt")
        public void testFunctionParameter() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/FunctionParameter.kt");
        }
        
        @TestMetadata("ImportDependency.kt")
        public void testImportDependency() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ImportDependency.kt");
        }
        
        @TestMetadata("ImportableEntityInExtensionLiteral.kt")
        public void testImportableEntityInExtensionLiteral() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ImportableEntityInExtensionLiteral.kt");
        }
        
        @TestMetadata("ImportedElementCopied.kt")
        public void testImportedElementCopied() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ImportedElementCopied.kt");
        }
        
        @TestMetadata("Inner.kt")
        public void testInner() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/Inner.kt");
        }
        
        @TestMetadata("Local.kt")
        public void testLocal() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/Local.kt");
        }
        
        @TestMetadata("NoImportForBuiltIns.kt")
        public void testNoImportForBuiltIns() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/NoImportForBuiltIns.kt");
        }
        
        @TestMetadata("NoImportForSamePackage.kt")
        public void testNoImportForSamePackage() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/NoImportForSamePackage.kt");
        }
        
        @TestMetadata("Object.kt")
        public void testObject() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/Object.kt");
        }
        
        @TestMetadata("ReferencedElementAlsoCopied.kt")
        public void testReferencedElementAlsoCopied() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/ReferencedElementAlsoCopied.kt");
        }
        
        @TestMetadata("TopLevelProperty.kt")
        public void testTopLevelProperty() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/TopLevelProperty.kt");
        }
        
        @TestMetadata("Trait.kt")
        public void testTrait() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/Trait.kt");
        }
        
        @TestMetadata("TypeParameter.kt")
        public void testTypeParameter() throws Exception {
            doTestCut("idea/testData/copyPaste/imports/TypeParameter.kt");
        }
        
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("InsertImportOnPasteTestGenerated");
        suite.addTestSuite(Copy.class);
        suite.addTestSuite(Cut.class);
        return suite;
    }
}
