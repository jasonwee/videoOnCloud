package play.learn.java.design.abstract_factory;

public class ElfKingdomFactory implements KingdomFactory {

	public Castle createCastle() {
		return new ElfCastle();
	}

	public King createKing() {
		return new ElfKing();
	}

	public Army createArmy() {
		return new ElfArmy();
	}

}
