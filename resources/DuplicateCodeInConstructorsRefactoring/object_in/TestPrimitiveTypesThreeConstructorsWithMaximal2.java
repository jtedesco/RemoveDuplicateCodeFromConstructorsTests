public class TestPrimitiveTypesThreeConstructorsWithMaximal2 {
	
	int x;
	double y;
	
	public TestPrimitiveTypesThreeConstructorsWithMaximal2() {
		this.x = 0;
		this.y = 0.0;
	}
	
	public TestPrimitiveTypesThreeConstructorsWithMaximal2(int x) {
		this.x = x;
		this.y = 0.0;
	}
	
	public TestPrimitiveTypesThreeConstructorsWithMaximal2(double y, int x) {
		this.x = x;
		this.y = y;
	}
}