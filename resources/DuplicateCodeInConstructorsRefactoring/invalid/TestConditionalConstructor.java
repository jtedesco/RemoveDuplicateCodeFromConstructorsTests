public class TestConditionalConstructor {
	
	int x;
	int y;
	
	public TestConditionalConstructor(int x, int y, boolean b) {
		if(b) {
			this.x = x;
			this.y = y;
		}
		else {
			this.x = y;
			this.y = x;
		}
	}
	
	public TestConditionalConstructor(int x, int y) {
		this.x = x;
		this.y = y;
	}
}