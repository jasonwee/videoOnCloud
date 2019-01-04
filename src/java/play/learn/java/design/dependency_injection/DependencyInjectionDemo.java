package play.learn.java.design.dependency_injection;

import com.google.inject.Guice;
import com.google.inject.Injector;


// https://java-design-patterns.com/patterns/dependency-injection/
public class DependencyInjectionDemo {

	public static void main(String[] args) {
	    SimpleWizard simpleWizard = new SimpleWizard();
	    simpleWizard.smoke();

	    
	    AdvancedWizard advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
	    advancedWizard.smoke();

	    
	    AdvancedSorceress advancedSorceress = new AdvancedSorceress();
	    advancedSorceress.setTobacco(new SecondBreakfastTobacco());
	    advancedSorceress.smoke();

	    Injector injector = Guice.createInjector(new TobaccoModule());
	    GuiceWizard guiceWizard = injector.getInstance(GuiceWizard.class);
	    guiceWizard.smoke();

	}

}
