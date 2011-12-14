public class TestPrimitiveTypesThreeConstructorsWithMaximal1 {

	int x;
	double y;

	public TestPrimitiveTypesThreeConstructorsWithMaximal1() {
		this(0, 0.0);
	}

	public TestPrimitiveTypesThreeConstructorsWithMaximal1(int x) {
		this(x, 0.0);
	}

	public TestPrimitiveTypesThreeConstructorsWithMaximal1(int x, double y) {
		this.x = x;
		this.y = y;
	}
}