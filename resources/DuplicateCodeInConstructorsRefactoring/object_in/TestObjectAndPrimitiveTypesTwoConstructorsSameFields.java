public class TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields {

	Object x;
	int y;

	public TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields(Object x) {
		this.x = x;
		this.y = 7;
	}

	public TestObjectAndPrimitiveTypesTwoConstructorsDifferentFields(int y) {
		this.y = y;
		this.x = new Object();
	}
}