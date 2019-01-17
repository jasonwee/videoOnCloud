package play.learn.java.design.prototype;

public class OrcBeast extends Beast {

	private String weapon;

	public OrcBeast(String weapon) {
		this.weapon = weapon;
	}

	public OrcBeast(OrcBeast orcBeast) {
		this.weapon = orcBeast.weapon;
	}

	@Override
	public Beast copy() {
		return new OrcBeast(this);
	}

	@Override
	public String toString() {
		return "Orcish wolf attacks with " + weapon;
	}
}
