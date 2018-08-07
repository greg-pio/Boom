import java.io.IOException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class Boom {

	@FXML 
	private Text text;
	@FXML
	private ProgressBar progress;
	@FXML
	private TextField input;
	@FXML
	private Text output;
		
	private Task<Integer> countdown;
	private int count = 60;
	
	private Task<Integer> prog;
	private int value = 0;
	public static final int MAX = 60;
	
	private Task<Integer> riddle;
	private Integer solution = (int) (Math.random() * 100);
	private Integer guess_Integer;
	private int guess_Int = -1;
	private IntegerStringConverter conv = new IntegerStringConverter();
	
	@FXML
	private void onNumEnter(KeyEvent a) {
		if (a.getCode().equals(KeyCode.ENTER)) {
			String getter = input.getText();
			
			try {
			guess_Integer = conv.fromString(getter);
			guess_Int = guess_Integer.intValue();
			}
			catch (NumberFormatException e) {
			}
			
		}		
	}
	
	private void stop() {
		countdown.cancel();
		prog.cancel();
		riddle.cancel();
	}
		
	@FXML
	private void initialize() {
				
		countdown = new Task<Integer>() {

			@Override
			protected Integer call() throws Exception {
				while (!isCancelled()) {
					count--;
					updateMessage(""+count);
					Thread.sleep(1000);
					if (count == 0) 
						return count;
					
				}
				return count;
			}
		};
		
		countdown.setOnSucceeded(e -> {
			Parent root;
	        try {
	            root = FXMLLoader.load(getClass().getClassLoader().getResource("End.fxml"));
	            Stage stage = new Stage();
	            stage.setTitle("End");
	            stage.setScene(new Scene(root, 200, 100));
	            stage.show();           
	            }
	        catch (IOException g) {
	            g.printStackTrace();
	        }					
		});
		
		prog = new Task<Integer>() {

			@Override
			protected Integer call() throws Exception {
				while (value < MAX) {
					value++;
					updateProgress(value, MAX);
					Thread.sleep(1000);
				}
				return value;
			}
		};
			
		riddle = new Task<Integer>() {

			@Override
			protected Integer call() throws Exception {
				while (!isCancelled()) {				
					
					if (guess_Int == -1)
						updateMessage("Enter your guess");
					else
						if (guess_Int < solution)
						updateMessage("Oops, you are too low!");
						else
							if (guess_Int > solution)
							updateMessage("Oops, you are to high!");
							else {
							updateMessage("You are correct!");
							stop();
							}
					Thread.sleep(1000);	
				
				}	
				return null;
			}
		};	
		
		text.textProperty().bind(countdown.messageProperty());
		progress.progressProperty().bind(prog.progressProperty());
		output.textProperty().bind(riddle.messageProperty());
		
		Thread thread1 = new Thread(countdown);
		thread1.setDaemon(true);
		thread1.start();		
		
		Thread thread2 = new Thread(prog);
		thread2.setDaemon(true);
		thread2.start();
		
		Thread thread3 = new Thread(riddle);
		thread3.setDaemon(true);
		thread3.start();	
					
	}
}


