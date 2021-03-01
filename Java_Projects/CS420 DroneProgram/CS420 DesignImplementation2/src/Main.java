

import java.io.IOException;
import java.net.URL;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		URL test = Main.class.getResource("/SmartFarmDashboard.fxml");
		loader.setLocation(Main.class.getResource("/SmartFarmDashboard.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
        stage.setTitle("Farm Dashboard");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
