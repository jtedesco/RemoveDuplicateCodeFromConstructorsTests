public class TestPrimitiveTypesThreeConstructorsWithSameFields2 {
	
	int x;
	double y;
	char z;
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields2(int x) {
		this.x = x;
		this.y = 5.0;
		this.z = 'a';
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields2(double y) {
		this.y = y;
		this.x = 42;
		this.z = 'b';
	}
	
	public TestPrimitiveTypesThreeConstructorsWithSameFields2(char z) {
		this.z = z;
		this.x = 10;
		this.y = 1.0;
	}
}