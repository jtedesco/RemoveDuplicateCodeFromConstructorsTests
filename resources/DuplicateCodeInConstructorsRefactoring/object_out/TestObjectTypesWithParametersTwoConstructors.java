public class TestObjectTypesWithParametersTwoConstructors {
	
	String s;
	
	public TestObjectTypesWithParametersTwoConstructors() {
		this("test");
	}
	
	public TestObjectTypesWithParametersTwoConstructors(String s) {
		this.s = new String(s);
	}
}