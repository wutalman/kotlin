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

package org.jetbrains.jet.resolve.constraintSystem;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.resolve.constraintSystem.AbstractConstraintSystemTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/testData/constraintSystem")
@InnerTestClasses({ConstraintSystemTestGenerated.CheckStatus.class})
public class ConstraintSystemTestGenerated extends AbstractConstraintSystemTest {
    @TestMetadata("1.bounds")
    public void test1() throws Exception {
        doTest("compiler/testData/constraintSystem/1.bounds");
    }
    
    public void testAllFilesPresentInConstraintSystem() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("compiler/testData/constraintSystem"), Pattern.compile("^(.+)\\.bounds$"), true);
    }
    
    @TestMetadata("compiler/testData/constraintSystem/checkStatus")
    public static class CheckStatus extends AbstractConstraintSystemTest {
        public void testAllFilesPresentInCheckStatus() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("compiler/testData/constraintSystem/checkStatus"), Pattern.compile("^(.+)\\.bounds$"), true);
        }
        
        @TestMetadata("conflictingConstraints.bounds")
        public void testConflictingConstraints() throws Exception {
            doTest("compiler/testData/constraintSystem/checkStatus/conflictingConstraints.bounds");
        }
        
        @TestMetadata("successful.bounds")
        public void testSuccessful() throws Exception {
            doTest("compiler/testData/constraintSystem/checkStatus/successful.bounds");
        }
        
        @TestMetadata("typeConstructorMismatch.bounds")
        public void testTypeConstructorMismatch() throws Exception {
            doTest("compiler/testData/constraintSystem/checkStatus/typeConstructorMismatch.bounds");
        }
        
        @TestMetadata("unknownParameters.bounds")
        public void testUnknownParameters() throws Exception {
            doTest("compiler/testData/constraintSystem/checkStatus/unknownParameters.bounds");
        }
        
        @TestMetadata("violatedUpperBound.bounds")
        public void testViolatedUpperBound() throws Exception {
            doTest("compiler/testData/constraintSystem/checkStatus/violatedUpperBound.bounds");
        }
        
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("ConstraintSystemTestGenerated");
        suite.addTestSuite(ConstraintSystemTestGenerated.class);
        suite.addTestSuite(CheckStatus.class);
        return suite;
    }
}
