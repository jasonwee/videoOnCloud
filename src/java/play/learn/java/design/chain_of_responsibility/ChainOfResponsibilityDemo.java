package play.learn.java.design.chain_of_responsibility;

// https://java-design-patterns.com/patterns/chain/
public class ChainOfResponsibilityDemo {

	public static void main(String[] args) {

		OrcKing king = new OrcKing();
		king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
		king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner"));
		king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));

	}

}
