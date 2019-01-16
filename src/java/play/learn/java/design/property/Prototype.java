package play.learn.java.design.property;

public interface Prototype {

	Integer get(Stats stat);

	boolean has(Stats stat);

	void set(Stats stat, Integer val);

	void remove(Stats stat);

}
