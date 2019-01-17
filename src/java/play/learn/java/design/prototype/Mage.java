package play.learn.java.design.prototype;

public abstract class Mage extends Prototype {
	
	  @Override
	  public abstract Mage copy() throws CloneNotSupportedException;

}
