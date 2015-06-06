package play.learn.java.annotation;

import java.lang.annotation.Repeatable;

@Repeatable(value = Schedules.class)
public @interface Schedule {
	
	String dayOfMonth() default "first";
	
	String dayOfWeek() default "Mon";
	
	int hour() default 12;

}
