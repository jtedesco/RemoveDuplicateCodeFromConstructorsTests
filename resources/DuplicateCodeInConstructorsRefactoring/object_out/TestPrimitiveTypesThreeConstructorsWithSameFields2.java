public class TestPrimitiveTypesThreeConstructorsWithSameFields2 {
	
	int x;
	double y;
	char z;
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields2(int x) {
		this(x, 5.0, 'a');
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields2(double y) {
		this(42, y, 'b');
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields2(char z) {
		this(10, 1.0, z);
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields2(int x, double y, char z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}