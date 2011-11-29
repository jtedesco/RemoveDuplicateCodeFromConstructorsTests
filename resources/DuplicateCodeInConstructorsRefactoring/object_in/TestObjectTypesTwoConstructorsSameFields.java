public class TestObjectTypesTwoConstructorsSameFields {
	
	Object x;
	String y;
	
	public TestObjectTypesTwoConstructorsSameFields(Object x) {
		this.x = x;
		y = "blah";
	}
	
	public TestObjectTypesTwoConstructorsSameFields(String y) {
		this.y = y;
		x = new Object();
	}
}