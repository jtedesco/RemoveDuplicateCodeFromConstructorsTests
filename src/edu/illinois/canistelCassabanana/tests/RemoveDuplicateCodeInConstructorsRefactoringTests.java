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

/**
 * Test suite for the refactoring
 * 
 * @author jon
 */
public class RemoveDuplicateCodeInConstructorsRefactoringTests extends AbstractSelectionTestCase {

	// Handle to test the refactoring
	private static RemoveDuplicateCodeInConstructorsRefactoringTestSetup testSetup;

	// Error messages for refactoring
	public static final String ERROR_REFACTORING_MESSAGE = "Error performing refactoring. ";
	public static final String EXPECTED_NO_DUPLICATE_CODE_MESSAGE = ERROR_REFACTORING_MESSAGE
			+ "No duplicate code found in constructors. "
			+ "This refactoring must be performed on code with two or more constructors that contain duplicate code.";
	public static final String EXPECTED_ONE_CONSTRUCTOR_MESSAGE = ERROR_REFACTORING_MESSAGE
			+ "Only one constructor found. "
			+ "This refactoring must be performed on code containing two or more constructors.";
	public static final String EXPECTED_NO_CONSTRUCTORS_MESSAGE = ERROR_REFACTORING_MESSAGE
			+ "No constructors found to refactor. "
			+ "This refactoring must be performed on code containing two or more constructors.";
	public static final String EXPECTED_UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred. See the error log for more details.";
	public static final String EXPECTED_NULL_CONSTRUCTORS_MESSAGE = EXPECTED_UNEXPECTED_ERROR_MESSAGE;
	public static final String EXPECTED_CHECKING_PRECONDITIONS_MESSAGE = "Checking preconditions for refactoring ...";
	public static final String EXPECTED_UNABLE_TO_REFACTOR = ERROR_REFACTORING_MESSAGE
			+ EXPECTED_UNEXPECTED_ERROR_MESSAGE;
	public static final String EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE = ERROR_REFACTORING_MESSAGE
			+ "Invalid name for helper function.";
	public static final String EXPECTED_HELPER_FUNCTION_EXISTING_NAME_MESSAGE = ERROR_REFACTORING_MESSAGE
			+ "Invalid name for helper function. Using an existing name for helper function not allowed.";
	public static final String EXPECTED_ASSIGNMENT_DEPENDENCY_MESSAGE = ERROR_REFACTORING_MESSAGE
			+ "Unable to resolve variable dependencies.";

	// UI text constants for this refactoring
	private static final String EXPECTED_REFACTORING_ID = "RemoveDuplicateCodeInConstructorsRefactoring";
	private static final String EXPECTED_REFACTORING_NAME = "Remove Duplicate Code in Constructors";
	private static final String EXPECTED_REFACTORING_DESCRIPTION = "Remove duplicate code from constructors by delegating "
			+ "common construction code to as few constructors or as possible.";

	// =====================================================================================
	// //
	// Helper functions and boiler plate code for running the test //
	// =====================================================================================
	// //

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
	 * @param packageFragment
	 *            The portion of the fragment from which to get the compilation
	 *            unit
	 * @param id
	 *            The name of the element for which to perform the refactoring
	 *            (the class)
	 * @param outputFolder
	 *            The folder in which to place the output of the refactoring
	 * @param useHelperFunction
	 *            TODO
	 * @param helperFunctionName
	 *            TODO
	 * @param accessModifier
	 *            TODO
	 */
	private void performTest(IPackageFragment packageFragment, String id, String outputFolder,
			boolean useHelperFunction, String helperFunctionName, String accessModifier) throws Exception {

		// Build the refactoring object to test
		ICompilationUnit unit = createCU(packageFragment, id);
		RemoveDuplicateCodeInConstructorsRefactoring refactoring = buildRefactoring(unit, useHelperFunction,
				helperFunctionName, accessModifier);

		// Check that the refactoring's conditions are met, and that it succeeds
		RefactoringStatus status = refactoring.checkAllConditions(new NullProgressMonitor());
		assertFalse(status.hasError());
		performTest(unit, refactoring, COMPARE_WITH_OUTPUT, getProofedContent(outputFolder, id), true);
	}

	/**
	 * Perform a test for a refactoring that should succeed.
	 * 
	 * @param packageFragment
	 *            The portion of the fragment from which to get the compilation
	 *            unit
	 * @param id
	 *            The name of the element for which to perform the refactoring
	 *            (the class)
	 * @param expectedErrorMessage
	 *            The error message expected from the test
	 * @param useHelperMethod
	 *            TODO
	 * @param helperFunctionName
	 *            TODO
	 * @param accessModifier
	 *            TODO
	 */
	private void performInvalidTest(IPackageFragment packageFragment, String id, String expectedErrorMessage,
			boolean useHelperMethod, String helperFunctionName, String accessModifier) throws Exception {

		// Build the refactoring object to test
		ICompilationUnit unit = createCU(packageFragment, id);
		RemoveDuplicateCodeInConstructorsRefactoring refactoring = buildRefactoring(unit, useHelperMethod,
				helperFunctionName, accessModifier);

		// Check that the refactoring's conditions are not met
		RefactoringStatus status = refactoring.checkAllConditions(new NullProgressMonitor());
		assertEquals(expectedErrorMessage, status.getMessageMatchingSeverity(status.FATAL));
		assertTrue(status.hasError());
	}

	/**
	 * Build the refactoring object
	 * 
	 * @param unit
	 *            The compilation unit on which this refactoring will be
	 *            performed
	 * @param useHelperMethod
	 *            TODO
	 * @param helperFunctionName
	 *            TODO
	 * @param accessModifier
	 *            TODO
	 * @return The constructed refactoring object
	 */
	private RemoveDuplicateCodeInConstructorsRefactoring buildRefactoring(ICompilationUnit unit,
			boolean useHelperMethod, String helperFunctionName, String accessModifier) throws Exception {
		initializePreferences();
		RemoveDuplicateCodeInConstructorsRefactoring refactoring = new RemoveDuplicateCodeInConstructorsRefactoring(
				unit, useHelperMethod, helperFunctionName, accessModifier);
		return refactoring;
	}

	/**
	 * Initialize the Java preferences for the Eclipse instance we're going to
	 * start
	 */
	@SuppressWarnings("deprecation")
	private void initializePreferences() {
		Preferences preferences = JavaCore.getPlugin().getPluginPreferences();
		preferences.setValue(JavaCore.CODEASSIST_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_PREFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_FIELD_SUFFIXES, "");
		preferences.setValue(JavaCore.CODEASSIST_STATIC_FIELD_SUFFIXES, "");
	}

	/**
	 * Run a test where the refactoring should succeed
	 * 
	 * @throws Exception
	 */
	private void validTest() throws Exception {
		performTest(testSetup.getObjectPackage(), getName(), "object_out", false, "", "public");
	}

	private void validTest(boolean useHelperFunction, String helperFunctionName, String accessModifier)
			throws Exception {
		performTest(testSetup.getObjectPackage(), getName(), "object_out", useHelperFunction, helperFunctionName,
				accessModifier);
	}

	private void invalidTest(String expectedErrorMessage) throws Exception {
		performInvalidTest(testSetup.getInvalidPackage(), getName(), expectedErrorMessage, false, "", "public");
	}

	private void invalidTest(String expectedErrorMessage, boolean useHelperFunction, String helperFunctionName,
			String accessModifier) throws Exception {
		performInvalidTest(testSetup.getInvalidPackage(), getName(), expectedErrorMessage, useHelperFunction,
				helperFunctionName, accessModifier);
	}

	// =====================================================================================
	// //
	// Tests that the refactoring fails on code with compile errors //
	// =====================================================================================
	// //

	public void testInvalidNaming() throws Exception {
		String syntaxErrors = "On line 4 : Syntax error on token \"default\", invalid VariableDeclaratorId\n"
				+ "On line 8 : Syntax error on token \"default\", invalid VariableDeclaratorId\n";
		invalidTest(MessageFormat.format(syntaxErrors, "TestInvalidNaming.java"));
	}

	public void testMissingBracket() throws Exception {
		String syntaxErrors = "On line 4 : Syntax error, insert \"}\" to complete ClassBody\n"
				+ "On line 6 : Duplicate method TestMissingBracket() in type TestMissingBracket\n"
				+ "On line 7 : Syntax error, insert \"}\" to complete MethodBody\n"
				+ "On line 10 : Duplicate method TestMissingBracket() in type TestMissingBracket\n"
				+ "On line 10 : Syntax error on token \"_test\", VariableDeclaratorId expected after this token\n"
				+ "On line 10 : Syntax error on token \")\", : expected\n"
				+ "On line 14 : Syntax error on token \"}\", delete this token\n";
		invalidTest(MessageFormat.format(syntaxErrors, "TestMissingBracket.java"));
	}

	public void testMissingSemicolon() throws Exception {
		String syntaxErrors = "On line 6 : Syntax error, insert \";\" to complete BlockStatements\n"
				+ "On line 10 : Syntax error, insert \";\" to complete BlockStatements\n";
		invalidTest(MessageFormat.format(syntaxErrors, "TestMissingSemicolon.java"));
	}

	// =====================================================================================
	// //
	// Tests that the refactoring fails when there's dependencies on field
	// assignments //
	// =====================================================================================
	// //

	/**
	 * Tests that the refactoring will fail on a simple primitive dependency
	 */
	public void testFieldAssignmentDependencyPrimitive() throws Exception {
		invalidTest(EXPECTED_ASSIGNMENT_DEPENDENCY_MESSAGE);
	}

	public void testFieldAssignmentDependencyPrimitive2() throws Exception {
		invalidTest(EXPECTED_ASSIGNMENT_DEPENDENCY_MESSAGE);
	}

	/**
	 * Tests that the refactoring will fail on a object dependency
	 */
	public void testFieldAssignmentDependencyObject() throws Exception {
		invalidTest(EXPECTED_ASSIGNMENT_DEPENDENCY_MESSAGE);
	}

	/**
	 * Tests that the refactoring will fail on a object dependency that was used
	 * in a function
	 */
	public void testFieldAssignmentDependencyObjectFunction() throws Exception {
		invalidTest(EXPECTED_ASSIGNMENT_DEPENDENCY_MESSAGE);
	}

	// =====================================================================================
	// //
	// Tests that the refactoring fails on classes with no constructors //
	// =====================================================================================
	// //

	/**
	 * Tests that the refactoring cannot be executed on an empty file.
	 */
	public void testEmptyFile() throws Exception {
		invalidTest(EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	/**
	 * Tests that the refactoring cannot be executed on an empty class.
	 */
	public void testEmptyClass() throws Exception {
		invalidTest(EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	/**
	 * Tests that the refactoring cannot be executed on a file containing
	 * only fields (no constructors).
	 */
	public void testOnlyFields() throws Exception {
		invalidTest(EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	/**
	 * Tests that the refactoring cannot be executed on a file containing
	 * only methods (no constructors).
	 */
	public void testOnlyMethods() throws Exception {
		invalidTest(EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	/**
	 * Tests that the refactoring cannot be executed on a file containing
	 * only fields and methods (no constructors).
	 */
	public void testFieldsAndMethods() throws Exception {
		invalidTest(EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	/**
	 * Tests that the refactoring cannot be executed on a file containing
	 * a method containing the class name (but not equal to the class name),
	 * with no constructors.
	 */
	public void testMethodWithClassSubstring() throws Exception {
		invalidTest(EXPECTED_NO_CONSTRUCTORS_MESSAGE);
	}

	// =====================================================================================
	// //
	// Tests that the refactoring fails on classes with only one constructor //
	// =====================================================================================
	// //

	/**
	 * Tests that the constructor cannot be executed when the class contains
	 * only
	 * one constructor and no other components of the class.
	 */
	public void testOneConstructorOnly() throws Exception {
		invalidTest(EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	/**
	 * Tests that the constructor cannot be executed when the class contains
	 * only
	 * one constructor with only fields aside from the constructor in the class.
	 */
	public void testOneConstructorWithFields() throws Exception {
		invalidTest(EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	/**
	 * Tests that the constructor cannot be executed when the class contains
	 * only
	 * one constructor with only methods aside from constructor in the class.
	 */
	public void testOneConstructorWithMethods() throws Exception {
		invalidTest(EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	/**
	 * Tests that the constructor cannot be executed when the class contains
	 * only
	 * one constructor with fields and methods also in the class.
	 */
	public void testOneConstructorWithFieldsAndMethods() throws Exception {
		invalidTest(EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	/**
	 * Tests that the constructor cannot be executed when the class contains
	 * only
	 * one constructor when the class also contains a method containing the
	 * class name.
	 */
	public void testOneConstructorWithMethodWithClassSubstring() throws Exception {
		invalidTest(EXPECTED_ONE_CONSTRUCTOR_MESSAGE);
	}

	// =====================================================================================
	// //
	// Tests that the refactoring fails on classes with no duplicate code //
	// =====================================================================================
	// //

	/**
	 * Difficult test that checks to see that the refactoring will fail if there
	 * is no way to do it
	 * given strange data flow between two constructors with the same parameter
	 * lists in different
	 * orders.
	 */
	public void testNoDuplicateCodeRandomValues() throws Exception {
		invalidTest(EXPECTED_UNABLE_TO_REFACTOR);
	}

	/**
	 * Tests that the refactoring does not proceed when the class contains do
	 * duplicate
	 * and contains multiple, constructors, one of which is a default
	 * constructor, and
	 * assigns the default value to fields.
	 */
	public void testNoDuplicateCodeWithDefaultConstructor() throws Exception {
		invalidTest(EXPECTED_NO_DUPLICATE_CODE_MESSAGE);
	}

	/**
	 * Tests that the refactoring does not proceed when the class contains do
	 * duplicate
	 * and contains multiple, non-default constructors. These constructors call
	 * a third,
	 * shared constructor (as if the refactoring had already been performed)
	 */
	public void testNoDuplicateCodeWithIndependentConstructors() throws Exception {
		invalidTest(EXPECTED_NO_DUPLICATE_CODE_MESSAGE);
	}

	/**
	 * Tests that the refactoring fails when the only 'master' does not directly
	 * assign
	 * parameters to fields (here it calls another constructor).
	 */
	public void testObjectTypesWithParametersTwoConstructors() throws Exception {
		invalidTest(EXPECTED_UNABLE_TO_REFACTOR);
	}

	/**
	 * Tests that the refactoring fails when the only 'master' does not directly
	 * assign
	 * parameters to fields (here it does some computation).
	 */
	public void testComputationalConstructor1() throws Exception {
		invalidTest(EXPECTED_UNABLE_TO_REFACTOR);
	}

	/**
	 * Tests that the refactoring fails when the only 'master' does not directly
	 * assign
	 * parameters to fields ().
	 */
	public void testComputationalConstructor2() throws Exception {
		invalidTest(EXPECTED_UNABLE_TO_REFACTOR);
	}

	// =====================================================================================
	// //
	// Tests that the refactoring fails with bad helper function names //
	// =====================================================================================
	// //

	public void testHelperNameFormatDefault() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "default", "public");
	}

	public void testHelperNameFormatEmpty() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "", "public");
	}

	public void testHelperNameFormatSpace() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, " ", "public");
	}

	public void testHelperNameFormatSpaced() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "bl ah", "public");
	}

	public void testHelperNameFormatSemicolon() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "bl;ah", "public");
	}

	public void testHelperNameFormatSlash() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "blah/", "public");
	}

	public void testHelperNameFormatExclamation() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "bl!ah", "public");
	}

	public void testHelperNameFormatAt() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "bl@ah", "public");
	}

	public void testHelperNameFormatPound() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "bl#ah", "public");
	}

	public void testHelperNameFormatNumber() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "9helper", "public");
	}

	public void testHelperNameFormatSpaceStart() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, " helper", "public");
	}

	public void testHelperNameFormatInt() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "int", "public");
	}

	public void testHelperNameFormatClassName() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_BAD_NAME_MESSAGE, true, "TestHelperNameFormatClassName", "public");
	}

	public void testHelperExistingName() throws Exception {
		invalidTest(EXPECTED_HELPER_FUNCTION_EXISTING_NAME_MESSAGE, true, "helper", "public");
	}

	// =====================================================================================
	// //
	// Tests that the refactoring succeeds with two constructors //
	// =====================================================================================
	// //

	/**
	 * Test that the refactoring succeeds in extracting the default value
	 * assigned in a default
	 * constructor with two constructors total.
	 */
	public void testPrimitiveTypesDefaultConstructor() throws Exception {
		validTest();
	}

	/**
	 * Test that the refactoring succeeds in extracting an explicit value
	 * assigned in a default
	 * constructor with two constructors total.
	 */
	public void testPrimitiveTypesDefaultConstructorExplicitValue() throws Exception {
		validTest();
	}

	/**
	 * Test the case where two constructors exist and each assign a different
	 * field, with two fields
	 * in the class. In this case, a new constructor should be created to assign
	 * both fields.
	 */
	public void testPrimitiveTypesTwoConstructorsDifferentFieldArguments1() throws Exception {
		validTest();
	}

	/**
	 * Test the case where two constructors exist and each assign the same
	 * fields, with two fields
	 * in the class. In this case, a new constructor should be created to assign
	 * both fields.
	 */
	public void testPrimitiveTypesTwoConstructorsDifferentFieldArguments2() throws Exception {
		validTest();
	}

	/**
	 * Test the case where two constructors exist and each assign the same
	 * fields, with two fields
	 * in the class. In this case, a new constructor should be created to assign
	 * both fields.
	 */
	public void testPrimitiveTypesTwoConstructorsSameFields() throws Exception {
		validTest();
	}

	/**
	 * Test the case when one constructor can delegate its work to the other,
	 * and assign a default null value
	 * to an object field.
	 */
	public void testObjectTypesDefaultConstructor() throws Exception {
		validTest();
	}

	/**
	 * Test the case when one constructor can delegate its work to the other,
	 * and assign an explicit value
	 * to an object field.
	 */
	public void testObjectTypesDefaultConstructorExplicitValue() throws Exception {
		validTest();
	}

	/**
	 * Test the case where two constructors exist and each assign the same
	 * fields, with two fields
	 * in the class. In this case, a new constructor should be created to assign
	 * both fields.
	 */
	public void testObjectTypesTwoConstructorsDifferentFields() throws Exception {
		validTest();
	}

	/**
	 * Test the case where two constructors exist and each assign the same
	 * fields, with two fields
	 * in the class. In this case, a new constructor should be created to assign
	 * both fields.
	 */
	public void testObjectTypesTwoConstructorsSameFields() throws Exception {
		validTest();
	}

	// =====================================================================================
	// //
	// Tests that the refactoring succeeds with three constructors //
	// =====================================================================================
	// //

	/**
	 * Test that the refactoring succeeds in extracting the default value
	 * assigned in a default
	 * constructor with three constructors total, and creates a new constructor.
	 */
	public void testPrimitiveTypesThreeConstructorsWithDefault() throws Exception {
		validTest();
	}

	/**
	 * Test that the refactoring succeeds in extracting an explicit value
	 * assigned in a default
	 * constructor with three constructors total, and creates a new constructor.
	 */
	public void testPrimitiveTypesThreeConstructorsWithDefaultExplicitValues() throws Exception {
		validTest();
	}

	/**
	 * Test that the refactoring succeeds in creating a new constructor for
	 * three non-default constructors
	 * and delegating their work to the new constructor.
	 */
	public void testPrimitiveTypesThreeConstructorsWithDifferentFields() throws Exception {
		validTest();
	}

	/**
	 * Test the case with three constructors where two constructors should
	 * delegate their assigned
	 * values to a third, existing constructor.
	 */
	public void testPrimitiveTypesThreeConstructorsWithMaximal1() throws Exception {
		validTest();
	}

	/**
	 * Test the case with three constructors where two constructors should
	 * delegate their assigned
	 * values to a third, existing constructor.
	 */
	public void testPrimitiveTypesThreeConstructorsWithMaximal2() throws Exception {
		validTest();
	}

	/**
	 * Test the case with three constructors where two constructors should
	 * delegate their assigned
	 * values to a third constructor that will be created.
	 */
	public void testPrimitiveTypesThreeConstructorsWithSameFields1() throws Exception {
		validTest();
	}

	/**
	 * Test the case with three constructors where two constructors should
	 * delegate their assigned
	 * values to a third constructor that will be created.
	 */
	public void testPrimitiveTypesThreeConstructorsWithSameFields2() throws Exception {
		validTest();
	}

	/**
	 * Test the case where the parameters of an existing constructor are NOT
	 * reordered to make sure that
	 * the correct parameters are passed to the correct fields.
	 */
	public void testNotReorderingParametersInExistingConstructor() throws Exception {
		validTest();
	}

	// =====================================================================================
	// //
	// Tests that the refactoring succeeds with two fields making helper //
	// =====================================================================================
	// //

	/**
	 * Test the refactoring succeeds when creating a helper function to assign
	 * two fields, instead of a new
	 * constructor.
	 */
	public void testHelperTwoFields() throws Exception {
		validTest(true, "helper", "public");
	}

	// =====================================================================================
	// //
	// Tests that the refactoring succeeds with two fields changing access
	// modifiers //
	// =====================================================================================
	// //

	/**
	 * Test that refactoring properly sets the access modifier to be 'private'
	 * if specified.
	 */
	public void testAccessPrivate() throws Exception {
		validTest(false, "", "private");
	}

	/**
	 * Test that refactoring properly sets the access modifier to be 'protected'
	 * if specified.
	 */
	public void testAccessProtected() throws Exception {
		validTest(false, "", "protected");
	}

	// =====================================================================================
	// //
	// Tests that the refactoring succeeds when there are unused fields (should
	// skip them) //
	// =====================================================================================
	// //

	/**
	 * Test that the refactoring properly removes duplicate code for unused
	 * primitive types.
	 */
	public void testUnusedPrimitiveTypes() throws Exception {
		validTest();
	}

	/**
	 * Test that the refactoring properly removes duplicate code for unused
	 * object types.
	 */
	public void testUnusedObjectTypes() throws Exception {
		validTest();
	}
}
