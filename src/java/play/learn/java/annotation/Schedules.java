package play.learn.java.annotation;

public @interface Schedules {
	
	
	Schedule[] value() default {};

}
