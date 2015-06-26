package play.learn.java.fx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationInterpolator extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Interpolator");
		final Group group = new Group();
		final Scene scene = new Scene(group, 600, 450);
		stage.setScene(scene);
		stage.show();
		
		final Rectangle rect = new Rectangle(100, 200, 100, 50);
		rect.setFill(Color.BLUE);
		
		group.getChildren().add(rect);
		
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		
		final KeyValue keyValue1 = new KeyValue(rect.xProperty(), 300);
		AnimationBooleanInterpolator yInterp = new AnimationBooleanInterpolator();
		final KeyValue keyValue2 = new KeyValue(rect.yProperty(), 0., yInterp);
		
		final KeyFrame kf1 = new KeyFrame(Duration.millis(2000), keyValue1);
		final KeyFrame kf2 = new KeyFrame(Duration.millis(2000), keyValue2);
		timeline.getKeyFrames().add(kf1);
		timeline.getKeyFrames().add(kf2);
		timeline.play();
		
		
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
