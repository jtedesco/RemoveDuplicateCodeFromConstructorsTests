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
		x = this.x;
		y = this.y;
	}
}