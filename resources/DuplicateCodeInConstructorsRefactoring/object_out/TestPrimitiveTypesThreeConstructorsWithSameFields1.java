public class TestPrimitiveTypesThreeConstructorsWithSameFields1 {
	
	int x;
	double y;
	char z;
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields1(int x) {
		this(x, 5.0, '\u0000');
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields1(double y) {
		this(42, y, '\u0000');
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields1(char z) {
		this(0, 0.0, z);
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields1(int x, double y, char z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}