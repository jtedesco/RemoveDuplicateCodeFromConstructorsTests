public class TestComputationalConstructor2 {
	
	int x;
	int y;
	
	public TestComputationalConstructor2(int a, int b, int c) {
		x = a * b;
		y = b * c;
	}
	
	public TestComputationalConstructor2(int a, int b) {
		this(a,b,1);
	}
}