package play.learn.java.fx;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LearnCreateFrame extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		createFrame(stage);
		addLeafNode(stage);
		animate(stage);
	}
	
	private void createFrame(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500, Color.GREEN);
		stage.setTitle("JavaFx Scene Graph Demo");
		stage.setScene(scene);
		stage.show();
	}
	
	private void addLeafNode(Stage stage) {
		   Group root = new Group();
	       Scene scene = new Scene(root, 500, 500, Color.BLACK);
	 
	       Rectangle r = new Rectangle(25,25,250,250);
	       r.setFill(Color.BLUE);
	       root.getChildren().add(r);
	 
	       stage.setTitle("JavaFX Scene Graph Demo");
	       stage.setScene(scene);
	       stage.show();
	}
	
	private void animate(Stage stage) {
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500, Color.BLACK);
		Rectangle r = new Rectangle(0, 0, 250, 250);
		r.setFill(Color.BLUE);
		root.getChildren().add(r);

		TranslateTransition translate = new TranslateTransition(
				Duration.millis(750));
		translate.setToX(390);
		translate.setToY(390);

		FillTransition fill = new FillTransition(Duration.millis(750));
		fill.setToValue(Color.RED);

		RotateTransition rotate = new RotateTransition(Duration.millis(750));
		rotate.setToAngle(360);

		ScaleTransition scale = new ScaleTransition(Duration.millis(750));
		scale.setToX(0.1);
		scale.setToY(0.1);

		ParallelTransition transition = new ParallelTransition(r, translate,
				fill, rotate, scale);
		transition.setCycleCount(Timeline.INDEFINITE);
		transition.setAutoReverse(true);
		transition.play();

		stage.setTitle("JavaFX Scene Graph Demo");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}



}
