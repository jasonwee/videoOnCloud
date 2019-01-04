package play.learn.java.design.dependency_injection;

import javax.inject.Inject;

public class GuiceWizard implements Wizard {
	

	  private Tobacco tobacco;

	  @Inject
	  public GuiceWizard(Tobacco tobacco) {
	    this.tobacco = tobacco;
	  }

	  @Override
	  public void smoke() {
	    tobacco.smoke(this);
	  }

}
