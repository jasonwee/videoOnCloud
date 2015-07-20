package play.learn.java.fx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import play.learn.java.fx.Branch.Type;

public class TreeGenerator {
	
	private List<Branch> generateBranches(Branch parentBranch, int depth) {
		List<Branch> branches = new ArrayList<>();
		
		if (parentBranch == null) { // add root branch
			branches.add(new Branch());			
		} else {
			if (parentBranch.length < 10) {
				return Collections.emptyList();
			}
			branches.add(new Branch(parentBranch, Type.LEFT, depth)); // add side left branch
			branches.add(new Branch(parentBranch, Type.RIGHT, depth)); // add side right branch
			branches.add(new Branch(parentBranch, Type.TOP, depth)); // add top branch
		}
		return branches;
	}

}
