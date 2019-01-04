package play.learn.java.design.dependency_injection;

import com.google.inject.AbstractModule;


public class TobaccoModule extends AbstractModule {
	
	@Override
	  protected void configure() {
	    bind(Tobacco.class).to(RivendellTobacco.class);
	  }

}
