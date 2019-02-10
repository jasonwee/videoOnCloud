package play.learn.java.design.service_layer;

import java.util.ArrayList;
import java.util.List;


public class MagicServiceImpl implements MagicService {

	private WizardDao wizardDao;
	private SpellbookDao spellbookDao;
	private SpellDao spellDao;

	/**
	 * Constructor
	 */
	public MagicServiceImpl(WizardDao wizardDao, SpellbookDao spellbookDao, SpellDao spellDao) {
		this.wizardDao = wizardDao;
		this.spellbookDao = spellbookDao;
		this.spellDao = spellDao;
	}

	@Override
	public List<Wizard> findAllWizards() {
		return wizardDao.findAll();
	}

	@Override
	public List<Spellbook> findAllSpellbooks() {
		return spellbookDao.findAll();
	}

	@Override
	public List<Spell> findAllSpells() {
		return spellDao.findAll();
	}

	@Override
	public List<Wizard> findWizardsWithSpellbook(String name) {
		Spellbook spellbook = spellbookDao.findByName(name);
		return new ArrayList<>(spellbook.getWizards());
	}

	@Override
	public List<Wizard> findWizardsWithSpell(String name) {
		Spell spell = spellDao.findByName(name);
		Spellbook spellbook = spell.getSpellbook();
		return new ArrayList<>(spellbook.getWizards());
	}

}
