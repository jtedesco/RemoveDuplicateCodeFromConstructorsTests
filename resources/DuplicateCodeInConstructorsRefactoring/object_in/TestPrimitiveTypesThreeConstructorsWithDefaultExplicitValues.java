public class TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues {

	int x;
	double y;

	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues() {
		this.x = 5;
		this.y = 7.0;
	}

	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues(int x) {
		this.x = x;
		this.y = 5.0;
	}

	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues(double y) {
		this.y = y;
		this.x = 2;
	}
}