public class TestObjectTypesWithParametersTwoConstructors {
	
	String s;
	
	public TestObjectTypesWithParametersTwoConstructors() {
		this.s = "test";
	}
	
	public TestObjectTypesWithParametersTwoConstructors(String s) {
		this.s = new String(s);
	}
}