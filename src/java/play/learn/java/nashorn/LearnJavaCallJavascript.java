package play.learn.java.nashorn;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * http://www.tutorialspoint.com/java8/java8_nashorn.htm
 * 
 * @author jason
 *
 */
public class LearnJavaCallJavascript {

	public static void main(String[] args) {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
		String fruit = "durian";
		
		Integer result = null;
		try {
			nashorn.eval("print('" + fruit + "')");
			result = (Integer)nashorn.eval("1 + 1");
		} catch (ScriptException e) {
			System.out.println("Error executing script: " + e.getMessage());
		}
		System.out.println(result.toString());

	}

}
