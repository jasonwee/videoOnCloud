package play.learn.java.design.service_layer;

public interface SpellDao extends Dao<Spell> {

	Spell findByName(String name);

}
