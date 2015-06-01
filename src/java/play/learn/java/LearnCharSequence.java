package play.learn.java;

public class LearnCharSequence {

	public static void main(String[] args) {
		CharSequence cs = "1 2   34";
		
		// chars() java8
		System.out.println(cs.chars().count());
		
		boolean found = cs.codePoints().anyMatch((int x) -> { if (x / 1 == 0) return true; else return false;});
		System.out.println(found);

	}

}
