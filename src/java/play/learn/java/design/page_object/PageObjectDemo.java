package play.learn.java.design.page_object;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

// https://java-design-patterns.com/patterns/page-object/
public class PageObjectDemo {

	private PageObjectDemo() {
		
	}

	public static void main(String[] args) {

		try {
			File applicationFile = new File(PageObjectDemo.class.getClassLoader().getResource("login.html").getPath());

			// should work for unix like OS (mac, unix etc...)
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(applicationFile);

			} else {
				// java Desktop not supported - above unlikely to work for Windows so try
				// following instead...
				Runtime.getRuntime().exec("cmd.exe start " + applicationFile);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
