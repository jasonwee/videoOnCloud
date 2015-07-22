package play.learn.java.fx;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import static java.lang.Math.*;
import static play.learn.java.fx.Util.addChildToParent;

public class Animator implements Runnable {
	
	public static final Duration BRANCH_GROWING_DURATION = Duration.seconds(2);
	public static final Duration GRASS_BECOME_GREEN_DURATION = Duration.seconds(5);
	public static final Duration GRASS_BECOME_YELLOW_DURATION = Duration.seconds(5);
	public static final Duration LEAF_BECOME_YELLOW_DURATION = Duration.seconds(5);
	public static final Duration WIND_CYCLE_DURATION = Duration.seconds(5);
	public static final Duration LEAF_APPEARING_DURATION = Duration.seconds(2);
	public static final Duration FLOWER_APPEARING_DURATION = Duration.seconds(1);
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
	
	private Transition seasonsAnimation(final Tree tree, final List<Blade> grass) {
		Transition spring = animateSpring(tree.leafage, grass);
		Transition flowers = animateFlowers(tree.flowers);
		Transition autumn = animateAutumn(tree.leafage, grass);
		
		SequentialTransition sequentialTransition = new SequentialTransition(spring, flowers, autumn);
		return sequentialTransition;
	}
	
	private Transition animateSpring(List<Leaf> leafage, List<Blade> grass) {
		ParallelTransition springAnimation = new ParallelTransition();
		
		for (final Blade blade : grass) {
			// grass become green
			springAnimation.getChildren().add(new FillTransition(GRASS_BECOME_GREEN_DURATION, blade, (Color) blade.getFill(), blade.SPRING_COLOR));
		}
		
		for (Leaf leaf : leafage) {
			// leafage appear
			ScaleTransition leafageAppear = new ScaleTransition(LEAF_APPEARING_DURATION, leaf);
			leafageAppear.setToX(1);
			leafageAppear.setToY(1);
			springAnimation.getChildren().add(leafageAppear);
		}
		return springAnimation;
	}
	
	private Transition animateFlowers(List<Flower> flowers) {
		ParallelTransition flowersAppearAndFallDown = new ParallelTransition();
		
		for (int i = 0; i < flowers.size(); i++) {
			final Flower flower = flowers.get(i);
			for (Ellipse petal : flower.getPetals()) {
				
				FadeTransition flowerAppear = new FadeTransition(FLOWER_APPEARING_DURATION, petal);
				flowerAppear.setToValue(1);
				flowerAppear.setDelay(FLOWER_APPEARING_DURATION.divide(3).multiply(i + 1));
				
				flowersAppearAndFallDown.getChildren().add(new SequentialTransition(new SequentialTransition(
						//flowes appearing
						flowerAppear,
						//fall down
						fakeFallDownAnimation(petal))));
			}
		}
		return flowersAppearAndFallDown;
	}
	
	private Transition animateAutumn(List<Leaf> leafage, List<Blade> grass) {
		ParallelTransition autumn = new ParallelTransition();
		
		//leafage animation
		ParallelTransition yellowLeafage = new ParallelTransition();
		ParallelTransition disappearLeafage = new ParallelTransition();
		
		for (final Leaf leaf : leafage) {
			// become yellow
			final FillTransition toYellow = new FillTransition(LEAF_BECOME_YELLOW_DURATION, leaf, null, leaf.AUTUMN_COLOR);
			yellowLeafage.getChildren().add(toYellow);
			
			// fall down
			Animation fakeLaefageDown = fakeFallDownEllipseAnimation(leaf, leaf.AUTUMN_COLOR, node -> {node.setScaleX(0); node.setScaleY(0);});
			
			// disappear
			disappearLeafage.getChildren().add(fakeLaefageDown);
		}
		
		// grass animation
		ParallelTransition grassBecomeYellowAnimation = new ParallelTransition();
		for (final Blade blade : grass) {
			// become yellow
			final FillTransition toYellow = new FillTransition(GRASS_BECOME_YELLOW_DURATION, blade, (Color)blade.getFill(), blade.AUTUMN_COLOR);
			toYellow.setDelay(Duration.seconds(1 * random()));
			grassBecomeYellowAnimation.getChildren().add(toYellow);
		}
		autumn.getChildren().addAll(grassBecomeYellowAnimation, new SequentialTransition(yellowLeafage, disappearLeafage));
		return autumn;
	}
	
	private Animation fakeFallDownAnimation(final Ellipse petalOld) {
		return fakeFallDownEllipseAnimation(petalOld, null, node ->node.setOpacity(0));
	}
	
	private Animation fakeFallDownEllipseAnimation(final Ellipse sourceEllipse, Color fakeColor, final HideMethod hideMethod) {
		final Ellipse fake = copyEllipse(sourceEllipse, fakeColor);
		addChildToParent(treeGenerator.content, fake);
		
		PauseTransition replaceFakeWithSource = new PauseTransition(Duration.ONE);
		replaceFakeWithSource.setDelay(Duration.minutes(0.9 * random() + 0.1));
		replaceFakeWithSource.setOnFinished(e -> {
			final Point2D position = treeGenerator.content.sceneToLocal(sourceEllipse.localToScene(0, 0));
			
			DoubleBinding sinPath = new DoubleBinding() {
				
				{
					bind(fake.translateYProperty());
				}
				
				@Override
				protected double computeValue() {
					return 50 * sin((fake.translateYProperty().doubleValue() - position.getY()) / 20);
				}
			};
			fake.setTranslateY(position.getY());
			fake.setCenterX(0);
			fake.setCenterY(0);
			fake.translateXProperty().bind(sinPath.add(position.getX()));
			fake.rotateProperty().bind(fake.translateYProperty().multiply(2).add(random() * 180));
			// replace source with fake
			fake.setOpacity(1);
			hideMethod.hide(sourceEllipse);
		});
		TranslateTransition fallDown = new TranslateTransition(Duration.seconds(30), fake);
		fallDown.setToY(random() * 30 + 1);
		FadeTransition disappear = new FadeTransition(Duration.seconds(2), fake);
		disappear.setDelay(Duration.seconds(5));
		disappear.setToValue(0);
		
		return new SequentialTransition(replaceFakeWithSource, 
				// fall down
				fallDown,
				// disappear
				disappear);
	}
	
	private Ellipse copyEllipse(Ellipse petalOld, Color color) {
		Ellipse ellipse = new Ellipse();
		ellipse.setRadiusX(petalOld.getRadiusX());
		ellipse.setRadiusY(petalOld.getRadiusY());
		
		if (color == null) {
			ellipse.setFill(petalOld.getFill());
		} else  {
			ellipse.setFill(color);
		}
		ellipse.setRotate(petalOld.getRotate());
		ellipse.setOpacity(0);
		return ellipse;
	}
	
	@FunctionalInterface
	private static interface HideMethod {
		void hide(Node node);
	}

}
