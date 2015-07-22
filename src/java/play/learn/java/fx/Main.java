package play.learn.java.fx;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * https://docs.oracle.com/javase/8/javafx/visual-effects-tutorial/tree-animation.htm
 * 
 * @author jason
 *
 */
public class Main extends Application {
	
	//private static final String SOURCE = Main.class.getResource("Ronkorb_etenraku_ryuteki.wav").toExternalForm();
    private static final int SCENE_WIDTH = 1000;
    private static final int SCENE_HEIGHT = 800;
    private static final int NUMBER_OF_BRANCH_GENERATIONS = 8;
    private static final int NUM_BLADES = 200;
    private Group rootContent;
    private Group grassContent;
    private Group treeContent;

	

	@Override
	public void start(Stage stage) throws Exception {
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.sizeToScene();
		stage.setScene(new AppScene());
		
		// close application
		final Text close = new Text("click to close");
		close.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		close.setStyle("-fx-background-color:transparent;-fx-text-fill:gray;");
		close.setOpacity(0);
		close.setOnMouseClicked(e -> {
			Platform.exit();
			System.exit(0);
		});
		StackPane stackPane = new StackPane(close);
		stackPane.setTranslateY(stage.getScene().getHeight() - 80);
		stackPane.getTransforms().add(new Rotate(180));
		stackPane.translateXProperty()
				.bind(stackPane.widthProperty().divide(2));
		rootContent.getChildren().add(stackPane);

		FadeTransition fadeIn = new FadeTransition(Duration.millis(500), close);
		fadeIn.setToValue(0.5);
		stage.getScene().setOnMouseEntered(e -> fadeIn.playFromStart());
		FadeTransition fadeOut = new FadeTransition(Duration.millis(500), close);
		fadeOut.setToValue(0);
		stage.getScene().setOnMouseExited(e -> fadeOut.play());
		stage.show();

		// close application
		stage.setOnCloseRequest(t -> {
			Platform.exit();
			System.exit(0);
		});

		new Animator(new TreeGenerator(treeContent,
				NUMBER_OF_BRANCH_GENERATIONS), new GrassGenerator(grassContent,
				NUM_BLADES)).run();

		// output FPS
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("FPS "
						+ com.sun.javafx.perf.PerformanceTracker
								.getSceneTracker(stage.getScene())
								.getInstantFPS());
			}
		}, 0, 1000);

		/*
		MediaPlayer sound = new MediaPlayer(new Media(SOURCE));
		sound.setCycleCount(MediaPlayer.INDEFINITE);
		sound.play();
		*/
		
	}
	
	private class AppScene extends Scene {
		public AppScene() {
			super(rootContent = new Group(), SCENE_WIDTH, SCENE_HEIGHT, Color.TRANSPARENT);
			rootContent.setClip(new Ellipse(0, SCENE_HEIGHT / 2, SCENE_WIDTH / 3, SCENE_HEIGHT /2 )); /// scene shape and size
			
			Rectangle background  = new Rectangle(-SCENE_WIDTH / 2, 0, SCENE_WIDTH, SCENE_HEIGHT);
			background.setFill(new LinearGradient(0, 0, 0, SCENE_HEIGHT, false, CycleMethod.NO_CYCLE, new Stop(0, Color.YELLOWGREEN), new Stop(0.3, Color.LIGHTBLUE))); // background color
			rootContent.getChildren().add(background);
			rootContent.getChildren().add(treeContent = new Group()); // tree layout
			rootContent.getChildren().add(grassContent = new Group()); // grass layout
			rootContent.getTransforms().addAll(new Translate(SCENE_WIDTH / 2, SCENE_HEIGHT), new Rotate(180));
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
