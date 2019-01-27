package play.learn.java.design.builder;

public enum Profession {
	WARRIOR, THIEF, MAGE, PRIEST;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
