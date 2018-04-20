package mock;

import java.util.ArrayList;
import java.util.List;

public class Skill {
	public static List getUISkills() {
		List lst = new ArrayList();
		lst.add("Extjs");
		lst.add("Jasmine");
		return lst;
	}

	public static List getBackendSkill() {
		List lst = new ArrayList();
		lst.add("Java");
		lst.add("JMockit");
		return lst;
	}
}
