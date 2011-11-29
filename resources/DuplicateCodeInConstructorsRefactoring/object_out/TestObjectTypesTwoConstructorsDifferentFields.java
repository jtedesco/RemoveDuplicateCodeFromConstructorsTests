public class TestObjectTypesTwoConstructorsDifferentFields {
	
	Object x;
	String y;
	
	public TestObjectTypesTwoConstructorsDifferentFields(Object x) {
		this(x,null);
	}
	
	public TestObjectTypesTwoConstructorsDifferentFields(String y) {
		this(null,y);
	}
	
	public TestObjectTypesTwoConstructorsDifferentFields(Object x, String y) {
		this.x = x;
		this.y = y;
	}
}