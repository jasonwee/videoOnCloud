package play.learn.java.design.prototype;

public class HeroFactoryImpl implements HeroFactory {

	private Mage mage;
	private Warlord warlord;
	private Beast beast;

	/**
	 * Constructor
	 */
	public HeroFactoryImpl(Mage mage, Warlord warlord, Beast beast) {
		this.mage = mage;
		this.warlord = warlord;
		this.beast = beast;
	}

	/**
	 * Create mage
	 */
	public Mage createMage() {
		try {
			return mage.copy();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * Create warlord
	 */
	public Warlord createWarlord() {
		try {
			return warlord.copy();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * Create beast
	 */
	public Beast createBeast() {
		try {
			return beast.copy();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
