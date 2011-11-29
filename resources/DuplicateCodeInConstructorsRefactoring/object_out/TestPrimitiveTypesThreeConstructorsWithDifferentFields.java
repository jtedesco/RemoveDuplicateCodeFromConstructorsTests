public class TestPrimitiveTypesThreeConstructorsWithDifferentFields {
	
	int x;
	double y;
	char z;
	
	public TestPrimitiveTypesThreeConstructorsWithDifferentFields(int x) {
		this(x, 0, 0);
	}
	
	public TestPrimitiveTypesThreeConstructorsWithDifferentFields(double y) {
		this(0, 0, y);
	}
	
	public TestPrimitiveTypesThreeConstructorsWithDifferentFields(char z) {
		this(0, z, 0);
	}

	public TestPrimitiveTypesThreeConstructorsWithDifferentFields(int _x, char _z, double _y) {
		x = _x;
		z = _z;
		y = _y;
	}
}