package play.learn.java.design.prototype;

public class ElfMage extends Mage {

	private String helpType;

	public ElfMage(String helpType) {
		this.helpType = helpType;
	}

	public ElfMage(ElfMage elfMage) {
		this.helpType = elfMage.helpType;
	}

	@Override
	public ElfMage copy() {
		return new ElfMage(this);
	}

	@Override
	public String toString() {
		return "Elven mage helps in " + helpType;
	}

}
