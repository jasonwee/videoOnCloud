package play.learn.java.classloader;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;


// https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
public class ClassLoader {
	
	public static List getClasseNames(String jarName) {
	    ArrayList classes = new ArrayList();
	    
	    System.out.println("Jar " + jarName );
	    
	    try {
	        JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
	        JarEntry jarEntry;

	        while (true) {
	            jarEntry = jarFile.getNextJarEntry();
	            if (jarEntry == null) {
	                break;
	            }
	            if (jarEntry.getName().endsWith(".class")) {
	                    System.out.println("Found " + jarEntry.getName().replaceAll("/", "\\."));
	                classes.add(jarEntry.getName().replaceAll("/", "\\."));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return classes;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		String pathToJar = "./lib/cassandra/apache-cassandra-2.0.7/lib/jamm-0.2.5.jar";
		JarFile jarFile = new JarFile(pathToJar);
		Enumeration<JarEntry> e = jarFile.entries();
		
		URL[] urls = { new URL("jar:file:" + pathToJar + "!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);
		
		while (e.hasMoreElements()) {
			JarEntry je = e.nextElement();
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			
			// -6 because of .class
			String className = je.getName().substring(0, je.getName().length()-6);
			className = className.replace('/', '.');
			System.out.println("className : " + className);
			Class c = cl.loadClass(className);
			System.out.println(c.getMethods()[0]);
			
			// to invoke the method, read about this
			//http://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string
		}
		

	}

}
