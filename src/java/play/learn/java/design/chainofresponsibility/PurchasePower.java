package play.learn.java.design.chainofresponsibility;

/**
 * In object-oriented design, the chain-of-responsibility pattern is a design pattern consisting of a source of command objects 
 * and a series of processing objects.[1] Each processing object contains logic that defines the types of command objects 
 * that it can handle; the rest are passed to the next processing object in the chain. A mechanism also exists for adding new 
 * processing objects to the end of this chain.
 * 
 * Below is an example of this pattern in Java. In this example we have different roles, each having a fixed purchasing limit 
 * and a successor. Every time a user in a role receives a purchase request that exceeds his or her limit, the request is 
 * passed to his or her successor.
 * 
 * @author jason
 *
 */
public abstract class PurchasePower {
	
	protected static final double BASE = 500;
	protected PurchasePower successor;
	
	abstract protected double getAllowable();
	abstract protected String getRole();
	
	public void setSuccessor(PurchasePower successor) {
		this.successor = successor;
	}
	
	public void processRequest(PurchaseRequest request) {
		if (request.getAmount() < this.getAllowable()) {
			System.out.println(this.getRole() + " will approve $" + request.getAmount());
		} else if (successor != null) {
			successor.processRequest(request);
		}
	}
}
