package edu.illinois.canistelCassabanana.tests;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.ui.tests.refactoring.infra.AbstractSelectionTestCase;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import edu.illinois.canistelCassabanana.RemoveDuplicateCodeInConstructorsRefactoring;

public class RemoveDuplicateCodeInConstructorsRefactoringTests extends AbstractSelectionTestCase {

	// Handle to test the refactoring
	private static RemoveDuplicateCodeInConstructorsRefactoringTestSetup testSetup;

	// Error messages for refactoring
	private static final String NO_DUPLICATE_CODE_MESSAGE = "No duplicate code found in constructors. " +
			"This refactoring must be performed on containing two or more constructors that contain duplicate code.";
	private static final String EXPECTED_ONE_CONSTRUCTOR_MESSAGE = "Only one constructor found. " +
			"This refactoring must be performed on containing two or more constructors.";
	private static final String EXPECTED_NO_CONSTRUCTORS_MESSAGE = "No constructors found to refactor. " +
			"This refactoring must be performed on containing two or more constructors.";
	private static final String EXPECTED_NULL_CONSTRUCTORS_MESSAGE = "Error finding constructors to refactor.";
	private static final String EXPECTED_CHECKING_PRECONDITIONS_MESSAGE = "Checking preconditions for refactoring ...";
	private static final String EXPECTED_COMPILE_ERRORS_MESSAGE = "Refactoring failed: ''{0}'' contains compile errors.";
	private static final String UNABLE_TO_REFACTOR = "Preconditions satisfied, but unable to refactor.";
	
	// UI text constants for this refactoring 
	private static final String EXPECTED_REFACTORING_ID = "RemoveDuplicateCodeInConstructorsRefactoring";
	private static final String EXPECTED_REFACTORING_NAME = "Remove Duplicate Code in Constructors";
	private static final String EXPECTED_REFACTORING_DESCRIPTION = "Remove duplicate code from constructors by delegating " +
			"common construction code to as few constructors or as possible.";

	// =====================================================================================
	//  Helper functions and boiler plate code for running the test
	// =====================================================================================
	
	
	public RemoveDuplicateCodeInConstructorsRefactoringTests(String name) {
		super(name);
	}

	public static Test suite() {
		TestSuite testSuite = new TestSuite(RemoveDuplicateCodeInConstructorsRefactoringTests.class);
		testSetup = new RemoveDuplicateCodeInConstructorsRefactoringTestSetup(testSuite);
		return testSetup;
	}

	public static Test setUpTest(Test test) {
		testSetup = new RemoveDuplicateCodeInConstructorsRefactoringTestSetup(test);
		return testSetup;
	}

	protected void setUp() throws Exception {
		super.setUp();
		fIsPreDeltaTest = true;
	}

	protected String getResourceLocation() {
		return "DuplicateCodeInConstructorsRefactoring/";
	}

	protected String adaptName(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1) + ".java";
	}
	
	/**
	 * Perform a test for a refactoring that should succeed.
	 * 
	 * 	@param packageFragment	The portion of the fragment from which to get the compilation unit
	 * 	@param id				The name of the element for which to perform the refactoring (the class)				
	 * 	@param outputFolder		The folder in which to place the output of the refactoring
	 */
	private void performTest(IPackageFragment packageFragment, String id, String outputFolder) throws Exception {
		
		// Build the refactoring object to test 
		ICompilationUnit unit = createCU(packageFragment, id);
		RemoveDuplicateCodeInConstructorsRefactoring refactoring = buildRefactoring(unit);
		
		// Check that the refactoring's conditions are met, and that it succeeds
		RefactoringStatus status = refactoring.checkAllConditions(new NullProgressMonitor());
		assertFalse(status.hasError());
		performTest(unit, refactoring, COMPARE_WITH_OUTPUT, getProofedContent(outputFolder, id), true);
	}

	/**
	 * Perform a test for a refactoring that should succeed.
	 * 
	 * 	@param packageFragment		The portion of the fragment from which to get the compilation unit
	 * 	@param id					The name of the element for which to perform the refactoring (the class)
	 *  @param expectedErrorMessage	The error message expected from the test			
	 */
	private void performInvalidTest(IPackageFragment packageFragment, String id, String expectedErrorMessage) throws Exception {
		
		// Build the refactoring object to test 
		ICompilationUnit unit = createCU(packageFragment, id);
		RemoveDuplicateCodeInConstructorsRefactoring refactoring = buildRefactoring(unit);
		
		// Check that the refactoring's conditions are not met
		RefactoringStatus status = refactoring.checkAllConditions(new NullProgressMonitor());
		assertEquals(expectedErrorMessage, status.getMessageMatchingSeverity(status.FATAL));
		assertTrue(status.hasError());
	}

	/**
	 * Build the refactoring object
	 * 
	 * 	@param  unit The compilation unit on which this refactoring will be performed	
	 * 	@return		 The constructed refactoring object 
	 */
	private RemoveDuplicateCodeInConstructorsRefactoring buildRefactoring(ICompilationUnit unit) throws Exception {
		initializePreferences();
		RemoveDuplicateCodeInConstructorsRefactoring refactoring = new RemoveDuplicateCodeInConstructorsRefactoring(unit);
		return refactoring;
	}

	@SuppressWarnings("deprecation")
	private void initializePreferences() {
		Preferences preferences = JavaCore.getPlugin().getPluginPreferences();
		preferences.setValue(JavaCore.CODEASSIST_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_FIELD_SUFFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_SUFFIXES, "");
	}
	
	private void validTest(String fieldName) throws Exception {
		performTest(testSetup.getObjectPackage(), getName(), "object_out");
	}

	private void invalidTest(String fieldName, String expectedErrorMessage) throws Exception {
		performInvalidTest(testSetup.getInvalidPackage(), getName(), expectedErrorMessage);
	}

	// =====================================================================================
	//  Tests that the refactoring fails on code with compile errors
	// =====================================================================================
	
	public void testInvalidNaming() throws Exception {
		String syntaxErrors = "On line 4 : Syntax error on token \"default\", invalid VariableDeclaratorId\n"
				+ "On line 8 : Syntax error on token \"default\", invalid VariableDeclaratorId\n";
		invalidTest("TestInvalidNaming", MessageFormat.format(syntaxErrors, "TestInvalidNaming.java"));
	}

	public void testMissingBracket() throws Exception {
		String syntaxErrors = "On line 4 : Syntax error, insert \"}\" to complete ClassBody\n" 
							+ "On line 6 : Duplicate method TestMissingBracket() in type TestMissingBracket\n"
							+ "On line 7 : Syntax error, insert \"}\" to complete MethodBody\n"
							+ "On line 10 : Duplicate method TestMissingBracket() in type TestMissingBracket\n"
							+ "On line 10 : Syntax error on token \"_test\", VariableDeclaratorId expected after this token\n"
							+ "On line 10 : Syntax error on token \")\", : expected\n"
							+ "On line 14 : Syntax error on token \"}\", delete this token\n";
		invalidTest("TestMissingBracket", MessageFormat.format(syntaxErrors, "TestMissingBracket.java"));
	}

	public void testMissingSemicolon() throws Exception {
		String syntaxErrors = "On line 6 : Syntax error, insert \";\" to complete BlockStatements\n"
							+ "On line 10 : Syntax error, insert \";\" to complete BlockStatements\n";
		invalidTest("TestMissingSemicolon", MessageFormat.format(syntaxErrors, "TestMissingSemicolon.java"));
	}
	
	// =====================================================================================
	//  Tests that the refactoring fails on classes with no constructors
	// =====================================================================================

	public void testEmptyFile() throws Exception {
		invalidTest("TestEmptyFile", EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	public void testEmptyClass() throws Exception {
		invalidTest("TestEmptyClass", EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	public void testOnlyFields() throws Exception {
		invalidTest("TestOnlyFields", EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	public void testOnlyMethods() throws Exception {
		invalidTest("TestOnlyMethods", EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	public void testFieldsAndMethods() throws Exception {
		invalidTest("TestFieldsAndMethods", EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	public void testMethodWithClassSubstring() throws Exception {
		invalidTest("TestMethodWithClassSubstring", EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	
	// =====================================================================================
	//  Tests that the refactoring fails on classes with only one constructor
	// =====================================================================================

	public void testOneConstructorOnly() throws Exception {
		invalidTest("TestOneConstructorOnly", EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	public void testOneConstructorWithFields() throws Exception {
		invalidTest("TestOneConstructorWithFields", EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	public void testOneConstructorWithMethods() throws Exception {
		invalidTest("TestOneConstructorWithMethods", EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	public void testOneConstructorWithFieldsAndMethods() throws Exception {
		invalidTest("TestOneConstructorWithFieldsAndMethods", EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	public void testOneConstructorWithMethodWithClassSubstring() throws Exception {
		invalidTest("TestOneConstructorWithMethodWithClassSubstring", EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}
	
	// =====================================================================================
	//  Tests that the refactoring fails on classes with no duplicate code
	// =====================================================================================
	
	public void testNoDuplicateCodeRandomValues() throws Exception {
		invalidTest("TestNoDuplicateCodeRandomValues", UNABLE_TO_REFACTOR);
	}

	public void testNoDuplicateCodeWithDefaultConstructor() throws Exception {
		invalidTest("TestNoDuplicateCodeWithDefaultConstructor", NO_DUPLICATE_CODE_MESSAGE);
	}

	public void testNoDuplicateCodeWithIndependentConstructors() throws Exception {
		invalidTest("TestNoDuplicateCodeWithIndependentConstructors", NO_DUPLICATE_CODE_MESSAGE);
	}
	
	// =====================================================================================
	//  Tests that the refactoring succeeds with two constructors
	// =====================================================================================
	
	public void testPrimitiveTypesDefaultConstructor() throws Exception {
		validTest("TestPrimitiveTypesDefaultConstructor");
	}
	
	public void testPrimitiveTypesDefaultConstructorExplicitValue() throws Exception {
		validTest("TestPrimitiveTypesDefaultConstructorExplicitValue");
	}
	
	public void testPrimitiveTypesTwoConstructorsDifferentFieldArguments1() throws Exception {
		validTest("TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1");
	}
	
	public void testPrimitiveTypesTwoConstructorsDifferentFieldArguments2() throws Exception {
		validTest("TestPrimitiveTypesTwoConstructorsDifferentFieldArguments2");
	}
	
	public void testPrimitiveTypesTwoConstructorsSameFields() throws Exception {
		validTest("TestPrimitiveTypesTwoConstructorsSameFields");
	}
	
	public void testObjectTypesDefaultConstructor() throws Exception {
		validTest("TestObjectTypesDefaultConstructor");
	}
	
	public void testObjectTypesDefaultConstructorExplicitValue() throws Exception {
		validTest("TestObjectTypesDefaultConstructorExplicitValue");
	}
	
	public void testObjectTypesTwoConstructorsDifferentFields() throws Exception {
		validTest("TestObjectTypesTwoConstructorsDifferentFields");
	}
	
	public void testObjectTypesTwoConstructorsSameFields() throws Exception {
		validTest("TestObjectTypesTwoConstructorsSameFields");
	}
	
	// =====================================================================================
	//  Tests that the refactoring succeeds with three constructors
	// =====================================================================================
	
	public void testPrimitiveTypesThreeConstructorsWithDefault() throws Exception {
		validTest("TestPrimitiveTypesThreeConstructorsWithDefault");
	}
	
	public void testPrimitiveTypesThreeConstructorsWithDefaultExplicitValues() throws Exception {
		validTest("TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues");
	}
	
	public void testPrimitiveTypesThreeConstructorsWithDifferentFields() throws Exception {
		validTest("TestPrimitiveTypesThreeConstructorsWithDifferentFields");
	}
	
	public void testPrimitiveTypesThreeConstructorsWithMaximal1() throws Exception {
		validTest("TestPrimitiveTypesThreeConstructorsWithMaximal1");
	}
	
	public void testPrimitiveTypesThreeConstructorsWithMaximal2() throws Exception {
		validTest("TestPrimitiveTypesThreeConstructorsWithMaximal");
	}
	
	public void testPrimitiveTypesThreeConstructorsWithSameFields1() throws Exception {
		validTest("TestPrimitiveTypesThreeConstructorsWithSameFields");
	}
	
	public void testPrimitiveTypesThreeConstructorsWithSameFields2() throws Exception {
		validTest("TestPrimitiveTypesThreeConstructorsWithSameFields2");
	}
	
	
	// =====================================================================================
	//  Tests that the refactoring succeeds with more advanced duplication
	//
	//  Not sure about these...
	// =====================================================================================

	public void testObjectTypesWithParametersTwoConstructors() throws Exception {
		validTest("TestObjectTypeWithParametersTwoConstructors");
	}
	
	public void testComputationalConstructor1() throws Exception {
		validTest("TestComputationalConstructor1");
	}
	
	public void testComputationalConstructor2() throws Exception {
		validTest("TestComputationalConstructor2");
	}
}
