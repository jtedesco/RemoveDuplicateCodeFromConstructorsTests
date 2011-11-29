public class TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues {
	
	int x;
	double y;
	
	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues() {
		x = 5;
		y = 7.0;
	}
	
	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues(int x) {
		this.x = x;
		y = 5.0;
	}
	
	public TestPrimitiveTypesThreeConstructorsWithDefaultExplicitValues(double y) {
		this.y = y;
		x = 2;
	}
}