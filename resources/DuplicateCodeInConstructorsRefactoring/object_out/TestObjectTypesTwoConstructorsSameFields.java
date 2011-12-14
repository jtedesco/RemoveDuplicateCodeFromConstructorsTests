public class TestObjectTypesTwoConstructorsSameFields {

	Object x;
	String y;

	public TestObjectTypesTwoConstructorsSameFields(Object x) {
		this(x, "blah");
	}

	public TestObjectTypesTwoConstructorsSameFields(String y) {
		this(new Object(), y);
	}

	public TestObjectTypesTwoConstructorsSameFields(Object x, String y) {
		this.x = x;
		this.y = y;
	}
}