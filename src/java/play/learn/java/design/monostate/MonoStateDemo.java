package play.learn.java.design.monostate;

// https://java-design-patterns.com/patterns/monostate/
public class MonoStateDemo {

	public static void main(String[] args) {
		LoadBalancer loadBalancer1 = new LoadBalancer();
		LoadBalancer loadBalancer2 = new LoadBalancer();
		loadBalancer1.serverRequest(new Request("Hello"));
		loadBalancer2.serverRequest(new Request("Hello World"));
	}

}
