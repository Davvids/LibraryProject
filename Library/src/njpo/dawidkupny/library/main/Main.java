package njpo.dawidkupny.library.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Dawid Kupny
 *
 */

public class Main extends Application {
	private String cssPath;

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/njpo/dawidkupny/library/view/LoginPane.fxml"));
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");

		cssPath = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(cssPath);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
