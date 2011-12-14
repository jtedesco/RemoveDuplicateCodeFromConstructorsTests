public class TestObjectTypesTwoConstructorsSameFields {

	Object x;
	String y;

	public TestObjectTypesTwoConstructorsSameFields(Object x) {
		this.x = x;
		this.y = "blah";
	}

	public TestObjectTypesTwoConstructorsSameFields(String y) {
		this.y = y;
		this.x = new Object();
	}
}