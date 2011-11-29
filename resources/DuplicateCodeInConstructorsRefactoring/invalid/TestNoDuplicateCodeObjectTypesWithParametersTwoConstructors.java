public class TestNoDuplicateCodeWithIndependentConstructors {
	
	String s;
	
	public TestNoDuplicateCodeWithIndependentConstructors() {
		
	}
	
	public TestNoDuplicateCodeWithIndependentConstructors(String s) {
		this.s = new String(s);
	}
}