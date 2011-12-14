public class TestPrimitiveTypesThreeConstructorsWithDifferentFields {

	int x;
	double y;
	char z;

	public TestPrimitiveTypesThreeConstructorsWithDifferentFields(int x) {
		this(x, 0, (char) 0);
	}

	public TestPrimitiveTypesThreeConstructorsWithDifferentFields(double y) {
		this(0, y, (char) 0);
	}

	public TestPrimitiveTypesThreeConstructorsWithDifferentFields(char z) {
		this(0, 0, z);
	}

	public TestPrimitiveTypesThreeConstructorsWithDifferentFields(int x, double y, char z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}