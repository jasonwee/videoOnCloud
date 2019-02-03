package play.learn.java.design.mutex;

// https://java-design-patterns.com/patterns/mutex/
public class MutexDemo {
	public static void main(String[] args) {
		Mutex mutex = new Mutex();
		Jar jar = new Jar(1000, mutex);
		Thief peter = new Thief("Peter", jar);
		Thief john = new Thief("John", jar);
		peter.start();
		john.start();
	}

}
