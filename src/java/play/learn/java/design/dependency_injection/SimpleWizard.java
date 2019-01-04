package play.learn.java.design.dependency_injection;

public class SimpleWizard implements Wizard {
	
	private OldTobyTobacco tobacco = new OldTobyTobacco();

	@Override
	public void smoke() {
	    tobacco.smoke(this);
	}

}
