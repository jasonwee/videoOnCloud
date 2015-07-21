package play.learn.java.fx;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Animator implements Runnable {
	
	public static final Duration BRANCH_GROWING_DURATION = Duration.seconds(2);
	public static final Duration GRASS_BECOME_GREEN_DURATION = Duration.seconds(5);
	public static final Duration GRASS_BECOME_YELLOW_DURATION = Duration.seconds(5);
	public static final Duration LEAF_BECOME_YELLOW_DURATION = Duration.seconds(5);
	public static final Duration WIND_CYCLE_DURATION = Duration.seconds(5);
	public static final Duration LEAF_APPEARING_DURATION = Duration.seconds(2);
	private final TreeGenerator treeGenerator;
	private final GrassGenerator grassGenerator;
	
	Animator(TreeGenerator treeGeneerator, GrassGenerator grassGenerator) {
		this.treeGenerator = treeGeneerator;
		this.grassGenerator = grassGenerator;		
	}
	

	@Override
	public void run() {
		Tree tree = treeGenerator.generateTree();
		List<Blade> grass = grassGenerator.generateGrass();
		
		// branch growing animation
		SequentialTransition branchGrowingAnimation = new SequentialTransition();
		// wind animation
		ParallelTransition treeWindAnimation = new ParallelTransition();
		
		for (int i = 0; i < tree.generations.size(); i++) {
			List<Branch> branchGeneration = tree.generations.get(i);
			branchGrowingAnimation.getChildren().add(animateBranchGrowing(branchGeneration)); // create animation for current crown
			treeWindAnimation.getChildren().add(animateTreeWind(branchGeneration, i));
		}
		
		// main animation : grass bending, tree bending, tree growing, seasons changing
		final Transition all = new ParallelTransition(new GrassWindAnimation(grass), treeWindAnimation, new SequentialTransition(branchGrowingAnimation, seasonsAnimation(tree, grass)));
		all.play();
	}
	
	// animation for growing branching
	private Animation animateBranchGrowing(List<Branch> branchGeneration) {
		ParallelTransition sameDepthBranchAnimation = new ParallelTransition();
		for (final Branch branch : branchGeneration) {
			Timeline branchGrowingAnimation = new Timeline(new KeyFrame(BRANCH_GROWING_DURATION, new KeyValue(branch.base.endYProperty(), branch.length))); // line is growing by changing endY from 0 to brunch.length
			PauseTransition pauseTransition = new PauseTransition();
			pauseTransition.setOnFinished(t -> branch.base.setStrokeWidth(branch.length / 25));
			sameDepthBranchAnimation.getChildren().add(new SequentialTransition(
					// to set width from 0 to some value we use pause transition with duration. one millisecond
					// trick to show lines
					pauseTransition,
					branchGrowingAnimation
					));
		}
		return sameDepthBranchAnimation;
	}
	
	// animate wind. tree is bending
	private Animation animateTreeWind(List<Branch> branchGeneration, int depth) {
		ParallelTransition wind = new ParallelTransition();
		for (final Branch branch : branchGeneration) {
			final Rotate rotation = new Rotate(0);
			branch.getTransforms().add(rotation);
			
			Timeline windTimeline = new Timeline(new KeyFrame(WIND_CYCLE_DURATION, new KeyValue(rotation.angleProperty(), depth * 2)));
			windTimeline.setAutoReverse(true);
			windTimeline.setCycleCount(Animation.INDEFINITE);
			wind.getChildren().add(windTimeline);
		}
		return wind;
	}
	
	private Transition seasonAnimation(final Tree tree, final List<Blade> grass) {
		Transition spring = animateSpring(tree.leafage, grass);
		Transition flowers = animateFlowers(tree.flowers);
		Transition autumn = animateAutumn(tree.leafage, grass);
		
		SequentialTransition sequentialTransition = new SequentialTransition(spring, flowers, autumn);
		return sequentialTransition;
	}

}
