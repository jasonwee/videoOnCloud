package mock;

import java.util.ArrayList;
import java.util.List;

public class User {
	public List getSkill(String type) {
		if ("Both".equals(type)) {
			List lst = Skill.getBackendSkill();
			lst.add(Skill.getUISkills());
			return lst;
		}
		return null;
	}

}
