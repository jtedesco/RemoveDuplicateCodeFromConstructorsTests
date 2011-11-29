public class TestPrimitiveTypesTwoConstructorsSameFields{
	
	int x;
	double y;
	
	public TestPrimitiveTypesTwoConstructorsSameFields(int x) {
		this(x,5.0);
	}
	
	public TestPrimitiveTypesTwoConstructorsSameFields(double y) {
		this(3,y);
	}
	
	public TestPrimitiveTypesTwoConstructorsSameFields(int x, double y) {
		this.x = x;
		this.y = y;
	}
}