import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class End {

	@FXML
	private Button button;
	
	@FXML
	private void onButtonClick() {
		Platform.exit();
	}	
}
