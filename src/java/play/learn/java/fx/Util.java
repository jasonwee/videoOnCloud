package play.learn.java.fx;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;

public class Util {
	
	public static void addChildToParent(final Group parent, final Node child) {
		Platform.runLater(() -> parent.getChildren().add(child));
	}
}
