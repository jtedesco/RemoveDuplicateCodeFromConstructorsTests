public class TestPrimitiveTypesTwoConstructorsSameFields {

	int x;
	double y;

	public TestPrimitiveTypesTwoConstructorsSameFields(int x) {
		this.x = x;
		this.y = 5.0;
	}

	public TestPrimitiveTypesTwoConstructorsSameFields(double y) {
		this.x = 3;
		this.y = y;
	}
}