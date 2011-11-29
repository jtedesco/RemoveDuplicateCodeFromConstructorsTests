public class TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1{
	
	int x;
	double y;
	
	public TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1(double y) {
		this.y = y;
	}
	
	public TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1(int x){
		this.x = x;
	}
	
	public TestPrimitiveTypesTwoConstructorsDifferentFieldArguments1(int x, double y){
		this.x = x;
		this.y = y;
	}
}