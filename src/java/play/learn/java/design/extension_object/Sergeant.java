package play.learn.java.design.extension_object;

public class Sergeant implements SergeantExtension {
	private SergeantUnit unit;

	public Sergeant(SergeantUnit sergeantUnit) {
		this.unit = sergeantUnit;
	}

	@Override
	public void sergeantReady() {
		System.out.println("[Sergeant] " + unit.getName() + " is ready! ");
	}
}
