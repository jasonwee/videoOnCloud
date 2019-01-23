package play.learn.java.design.acyclic_visitor;

public class Hayes extends Modem {

	/**
	 * Accepts all visitors but honors only HayesVisitor
	 */
	@Override
	public void accept(ModemVisitor modemVisitor) {
		if (modemVisitor instanceof HayesVisitor) {
			((HayesVisitor) modemVisitor).visit(this);
		} else {
			System.out.println("Only HayesVisitor is allowed to visit Hayes modem");
		}

	}

	/**
	 * Hayes' modem's toString method
	 */
	@Override
	public String toString() {
		return "Hayes modem";
	}
}
