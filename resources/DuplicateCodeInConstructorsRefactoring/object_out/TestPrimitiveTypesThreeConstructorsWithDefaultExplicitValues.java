public class TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues {

	int x;
	double y;

	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues() {
		this(5, 7.0);
	}

	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues(int x) {
		this(x, 5.0);
	}

	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues(double y) {
		this(2, y);
	}

	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues(int x, double y) {
		this.x = x;
		this.y = y;
	}
}