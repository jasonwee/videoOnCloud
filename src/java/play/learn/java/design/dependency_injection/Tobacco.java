package play.learn.java.design.dependency_injection;

public abstract class Tobacco {

	  public void smoke(Wizard wizard) {
	    System.out.println(wizard.getClass().getSimpleName() + " smoking " + this.getClass().getSimpleName());
	  }

}
