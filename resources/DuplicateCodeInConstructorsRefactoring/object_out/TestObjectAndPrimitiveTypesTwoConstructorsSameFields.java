public class TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields {

	Object x;
	int y;

	public TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields(Object x) {
		this(x, 7);
	}

	public TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields(int y) {
		this(new Object(), y);
	}

	public TestObjectAndPrimitiveTypesTwoConstructorsSameFields(Object x, int y) {
		this.x = x;
		this.y = y;
	}
}