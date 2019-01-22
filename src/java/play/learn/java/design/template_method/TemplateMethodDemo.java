package play.learn.java.design.template_method;

// https://java-design-patterns.com/patterns/template-method/
public class TemplateMethodDemo {

	public static void main(String[] args) {
		HalflingThief thief = new HalflingThief(new HitAndRunMethod());
		thief.steal();
		thief.changeMethod(new SubtleMethod());
		thief.steal();
	}
}
