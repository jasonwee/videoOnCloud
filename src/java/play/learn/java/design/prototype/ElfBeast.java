package play.learn.java.design.prototype;

public class ElfBeast extends Beast {

	private String helpType;

	public ElfBeast(String helpType) {
		this.helpType = helpType;
	}

	public ElfBeast(ElfBeast elfBeast) {
		this.helpType = elfBeast.helpType;
	}

	@Override
	public Beast copy() {
		return new ElfBeast(this);
	}

	@Override
	public String toString() {
		return "Elven eagle helps in " + helpType;
	}

}
