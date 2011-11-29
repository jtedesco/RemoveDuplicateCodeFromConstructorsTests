public class TestNoDuplicateCodeWithIndependentConstructors {
	
  int x;
  boolean boo;

  public TestNoDuplicateCodeWithIndependentConstructors(boolean booboo){
    this(booboo, 0);
  }

  public TestNoDuplicateCodeWithIndependentConstructors(int spooky){
    this(false, spooky);
   
  }

  public TestNoDuplicateCodeWithIndependentConstructors(boolean booboo, int spooky){
    boo = booboo;
    x = spooky;
  }
}
