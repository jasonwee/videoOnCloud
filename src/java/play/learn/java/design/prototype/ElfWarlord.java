package play.learn.java.design.prototype;

public class ElfWarlord extends Warlord {

	private String helpType;

	public ElfWarlord(String helpType) {
		this.helpType = helpType;
	}

	public ElfWarlord(ElfWarlord elfWarlord) {
		this.helpType = elfWarlord.helpType;
	}

	@Override
	public ElfWarlord copy() {
		return new ElfWarlord(this);
	}

	@Override
	public String toString() {
		return "Elven warlord helps in " + helpType;
	}

}
