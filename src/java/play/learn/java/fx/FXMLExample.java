package play.learn.java.fx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLExample extends Application {
	
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
		
		Scene scene = new Scene(root, 300, 275);
		
		stage.setTitle("FXML Welcome");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
