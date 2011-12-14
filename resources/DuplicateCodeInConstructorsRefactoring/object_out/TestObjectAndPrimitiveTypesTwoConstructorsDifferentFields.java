public class TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields {

	Object x;
	int y;

	public TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields(Object x) {
		this(x, 0);
	}

	public TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields(int y) {
		this(null, y);
	}

	public TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields(Object x, int y) {
		this.x = x;
		this.y = y;
	}
}