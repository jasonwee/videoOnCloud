package play.learn.java.design.facade1;

// https://java-design-patterns.com/patterns/facade/
public class FacadeDemo {

	public static void main(String[] args) {
		DwarvenGoldmineFacade facade = new DwarvenGoldmineFacade();
		facade.startNewDay();
		facade.digOutGold();
		facade.endDay();
	}

}
