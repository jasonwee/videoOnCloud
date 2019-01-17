package play.learn.java.design.prototype;

public class OrcWarlord extends Warlord {

	private String weapon;

	public OrcWarlord(String weapon) {
		this.weapon = weapon;
	}

	public OrcWarlord(OrcWarlord orcWarlord) {
		this.weapon = orcWarlord.weapon;
	}

	@Override
	public OrcWarlord copy() {
		return new OrcWarlord(this);
	}

	@Override
	public String toString() {
		return "Orcish warlord attacks with " + weapon;
	}
}
