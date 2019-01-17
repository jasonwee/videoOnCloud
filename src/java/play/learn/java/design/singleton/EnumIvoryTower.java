package play.learn.java.design.singleton;

public enum EnumIvoryTower {
	INSTANCE;

	@Override
	public String toString() {
		return getDeclaringClass().getCanonicalName() + "@" + hashCode();
	}
}
