package play.learn.java.design.service_layer;

public interface WizardDao extends Dao<Wizard> {
	  Wizard findByName(String name);


}
