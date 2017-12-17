package play.learn.java;

public class LambdaMore {
	
	public void greet(Greeting greeting) {
		greeting.perform();
	}

	public static void main(String[] args) {
		LambdaMore lambdaMore = new LambdaMore();
		HelloWorldGreeting helloWorldGreeting = new HelloWorldGreeting();
		lambdaMore.greet(helloWorldGreeting);
		
		Greeting myLambdaFunction = () -> System.out.println("Hello World!");
		Myadd addFunction = (int a, int b) -> a+b;
	}
	
	public interface Greeting {
		void perform();
	}
	
	public static class HelloWorldGreeting implements Greeting {

		@Override
		public void perform() {
			System.out.println("hello world");
		}
		
	}

}

interface MyLambda {
	void foo();
}

interface Myadd {
	int add(int x, int y);
}