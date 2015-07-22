package play.learn.java.fx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.Group;
import play.learn.java.fx.Branch.Type;

import static play.learn.java.fx.RandomUtil.getRandomIndex;
import static play.learn.java.fx.Util.addChildToParent;

public class TreeGenerator {
	
	public static final int FLOWERS_COUNT = 100;
	public final Group content;
	private final int treeDepth;
	
	public TreeGenerator(Group content, int treeDepth) {
		this.content = content;
		this.treeDepth = treeDepth;
	}
	
	public Tree generateTree() {
		Tree tree = new Tree(treeDepth);
		addChildToParent(content, tree);
		
		Branch root = new Branch();
		addChildToParent(tree, root);
		tree.generations.get(0).add(root); //root branch
		
		for (int i = 1; i < treeDepth; i++) {
			for (Branch parentBranch : tree.generations.get(i - 1)) {
				List<Branch> newBranches = generateBranches(parentBranch, i);
				if (newBranches.isEmpty()) {
					tree.crown.add(parentBranch);
				}
				tree.generations.get(i).addAll(generateBranches(parentBranch, i));
			}
		}
		for (Branch crownBranch : tree.generations.get(treeDepth - 1)) {
			tree.crown.add(crownBranch);
		}
		tree.leafage.addAll(generateLeafage(tree.crown));
		tree.flowers.addAll(generateFlowers(tree.crown));
		return tree;
	}
	
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
	
	private List<Leaf> generateLeafage(List<Branch> crown) {
		List<Leaf> leafage = new ArrayList<>();
		for (Branch branch : crown) {
			Leaf leaf = new Leaf(branch);
			leafage.add(leaf);
			addChildToParent(branch, leaf);
		}
		return leafage;
	}
	
	private List<Flower> generateFlowers(List<Branch> crown) {
		List<Flower> flowers = new ArrayList<>(FLOWERS_COUNT);
		for (int i = 0; i < FLOWERS_COUNT; i++) {
			Branch branch = crown.get(getRandomIndex(0, crown.size() - 1));
			final Flower flower = new Flower(branch);
			addChildToParent(branch, flower);
			flowers.add(flower);
		}
		return flowers;
	}

}
