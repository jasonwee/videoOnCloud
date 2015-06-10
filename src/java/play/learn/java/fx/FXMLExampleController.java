package play.learn.java.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class FXMLExampleController {
	@FXML private Text actionTarget;
	
	@FXML protected void handleSubmitButtonAction(ActionEvent event) {
		actionTarget.setText("Sign in button pressed");
	}
	
}
