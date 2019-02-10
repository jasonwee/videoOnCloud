package play.learn.java.design.service_layer;

import java.util.List;


public interface MagicService {
	List<Wizard> findAllWizards();

	List<Spellbook> findAllSpellbooks();

	List<Spell> findAllSpells();

	List<Wizard> findWizardsWithSpellbook(String name);

	List<Wizard> findWizardsWithSpell(String name);
}
