package njpo.dawidkupny.library.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController implements Initializable{
	private final String log = "root";


    @FXML
    private TextField usernameField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
    	if(usernameField.getText().equals(log) && passwordField.getText().equals(log)) {
    		((Node)(event.getSource())).getScene().getWindow().hide();
    		Parent parent = FXMLLoader.load(getClass().getResource("/njpo/dawidkupny/library/view/MainPane.fxml"));
    		Stage stage = new Stage();
    		Scene scene = new Scene(parent);
    		stage.setScene(scene);
    		stage.setTitle("Library");
    		stage.show();


    	} else {
    		errorLabel.setText("Username or Password invalid. Try again.");
    	}
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginButton.setOnAction(x -> {
			try {
				loginButtonAction(x);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

}
