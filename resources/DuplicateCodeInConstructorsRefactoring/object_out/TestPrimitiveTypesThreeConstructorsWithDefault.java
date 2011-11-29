public class TestPrimitiveTypesThreeConstructorsWithDefault {
	
	int x;
	double y;
	
	public TestPrimitiveTypesThreeConstructorsWithDefault() {
		this(0, 0.0);
	}
	
	public TestPrimitiveTypesThreeConstructorsWithDefault(int x) {
		this(x, 0.0);
	}
	
	public TestPrimitiveTypesThreeConstructorsWithDefault(double y) {
		this(0, y);
	}
	
	public TestPrimitiveTypesThreeConstructorsWithDefault(int x, double y) {
		this.x = x;
		this.y = y;
	}
}