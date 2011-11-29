public class TestComputationalConstructor1 {
	
	int x;
	int y;
	
	public TestComputationalConstructor1(int a, int b, int c) {
		x = a + b;
		y = b + c;
	}
	
	public TestComputationalConstructor1(int a, int b) {
		x = a + b;
		y = b;
	}
}