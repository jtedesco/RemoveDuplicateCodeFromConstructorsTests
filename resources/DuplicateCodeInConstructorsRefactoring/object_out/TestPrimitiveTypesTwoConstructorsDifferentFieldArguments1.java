public class TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1 {

	int x;
	double y;

	public TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1(double y) {
		this(0, y);
	}

	public TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1(int x) {
		this(x, 0);
	}

	public TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1(int x, double y) {
		this.x = x;
		this.y = y;
	}
}