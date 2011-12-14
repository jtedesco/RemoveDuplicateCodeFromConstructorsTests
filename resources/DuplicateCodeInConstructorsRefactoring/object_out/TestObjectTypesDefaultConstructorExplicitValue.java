public class TestObjectTypesDefaultConstructorExplicitValue {

	Object x;

	public TestObjectTypesDefaultConstructorExplicitValue() {
		this(new Object());
	}

	public TestObjectTypesDefaultConstructorExplicitValue(Object x) {
		this.x = x;
	}
}