package play.learn.java.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * https://docs.oracle.com/javase/8/javafx/get-started-tutorial/form.htm
 * https://docs.oracle.com/javase/8/javafx/get-started-tutorial/css.htm
 * 
 * @author jason
 *
 */
public class LearnCreateForm extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("JavaFX Welcome");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text sceneTitle = new Text("Welcome");
		//sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		sceneTitle.setId("welcome-text");
		grid.add(sceneTitle, 0, 0, 2, 1);
		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);
		
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		
		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);
		
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		
		// for debug purpose
		//grid.setGridLinesVisible(true);
		
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		final Text actionTarget = new Text();
		grid.add(actionTarget, 1, 6);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				//actionTarget.setFill(Color.FIREBRICK);
				actionTarget.setId("actionTarget");
				actionTarget.setText("Sign in button pressed");
			}
		});
		
		Scene scene = new Scene(grid, 300, 275);
		stage.setScene(scene);
		scene.getStylesheets().add(LearnCreateForm.class.getResource("Login.css").toExternalForm());
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}



}
