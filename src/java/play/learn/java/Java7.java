package play.learn.java;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Java7 {

	public static void main(String args[]) {
		
		Java7 myClass = new Java7();
	        Class c = myClass.getClass();
	        try {            
	            System.out.println(c.getMethod("getNumber", null).toString());
	            System.out.println(c.getDeclaredMethod("setNumber", null).toString());
	        } catch (NoSuchMethodException | SecurityException e) {}
	    }
	    public Integer getNumber() {
	        return 2;
	    }
	    public void setNumber(Integer n) {
	    }
		
	    
}