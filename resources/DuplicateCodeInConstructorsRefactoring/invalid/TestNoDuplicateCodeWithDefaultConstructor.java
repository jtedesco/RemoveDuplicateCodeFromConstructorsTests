public class TestNoDuplicateCodeWithDefaultConstructor {
	
  boolean boo;

  public TestNoDuplicateCodeWithDefaultConstructor(){
    this(false);
  }

  public TestNoDuplicateCodeWithDefaultConstructor(boolean booboo){
    boo = booboo;
  }
}
