public class TestPrimitiveTypesTwoConstructorsDifferentFieldArguments2{
	
	double y;
	int x;
	
	public TestPrimitiveTypesTwoConstructorsDifferentFieldArguments2(double y) {
		this(0,y);
	}
	
	public TestPrimitiveTypesTwoConstructorsDifferentFieldArguments2(int x) {
		this(x,0);
	}
	
	public TestPrimitiveTypesTwoConstructorsDifferentArguments2(double y, int x) {
		this.y = y;
		this.x = x;
	}
}