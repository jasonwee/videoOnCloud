package play.learn.java.design.abstract_factory;

public interface KingdomFactory {
	Castle createCastle();

	King createKing();

	Army createArmy();
}
