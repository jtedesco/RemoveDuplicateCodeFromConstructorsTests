public class TestPrimitiveTypesThreeConstructorsWithMaximal1 {
	
	int x;
	double y;
	
	public TestPrimitiveTypesThreeConstructorsWithMaximal1() {
		this.x = 0;
		this.y = 0.0;
	}
	
	public TestPrimitiveTypesThreeConstructorsWithMaximal1(int x) {
		this.x = x;
		this.y = 0.0;
	}
	
	public TestPrimitiveTypesThreeConstructorsWithMaximal1(int x, double y) {
		this.x = x;
		this.y = y;
	}
}