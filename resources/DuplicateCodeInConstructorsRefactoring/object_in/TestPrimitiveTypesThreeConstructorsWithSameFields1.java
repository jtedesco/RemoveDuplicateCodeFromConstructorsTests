public class TestPrimitiveTypesThreeConstructorsWithSameFields1 {
	
	int x;
	double y;
	char z;
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields1(int x) {
		this.x = x;
		this.y = 5.0;
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields1(double y) {
		this.y = y;
		this.x = 42;
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields1(char z) {
		this.z = z;
	}
}