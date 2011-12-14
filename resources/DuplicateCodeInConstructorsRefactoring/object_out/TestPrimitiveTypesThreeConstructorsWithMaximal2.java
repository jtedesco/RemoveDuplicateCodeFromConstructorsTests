public class TestPrimitiveTypesThreeConstructorsWithMaximal2 {

	int x;
	double y;

	public TestPrimitiveTypesThreeConstructorsWithMaximal2() {
		this(0, 0.0);
	}

	public TestPrimitiveTypesThreeConstructorsWithMaximal2(int x) {
		this(x, 0.0);
	}

	public TestPrimitiveTypesThreeConstructorsWithMaximal2(int x, double y) {
		this.x = x;
		this.y = y;
	}
}