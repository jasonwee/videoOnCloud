package play.learn.java.design.service_layer;

public interface SpellbookDao extends Dao<Spellbook> {

	Spellbook findByName(String name);

}
