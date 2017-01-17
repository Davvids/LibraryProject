package njpo.dawidkupny.library.controller;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddBookController implements Initializable{

    @FXML
    private Label messageAddBookButton;

    @FXML
    private TextField yearField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField titleField;

    @FXML
    private Button quitButton;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField publisherField;

    @FXML
    private Button addButton;

	ResultSet rs;
	String title, year, publisher, author, isbn;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DataBaseConnection dataBase = DataBaseConnection.getInstance();

		//dataBase.connect();

		quitButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	((Node)(event.getSource())).getScene().getWindow().hide();
	        }
	    });

		addButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	dataBase.connect();
	    		if(yearField.getText().equals("") || yearField.getText().charAt(0) == ' ' || titleField.getText().equals("") || titleField.getText().charAt(0) == ' '|| publisherField.getText().equals("") || publisherField.getText().charAt(0) == ' ' || authorField.getText().equals("") || authorField.getText().charAt(0) == ' ' || isbnField.getText().equals("") || isbnField.getText().charAt(0) == ' ') {
	        		messageAddBookButton.setText("Uzupelnij brakujace pola!");
	    		} else if(isbnField.getText().length() != 13) {
	        		messageAddBookButton.setText("Podales zly nr isbn");
	    		} else if(checkString(isbnField.getText()) == false){
	    			messageAddBookButton.setText("Podales zly nr isbn");
	    		} else {
		        	try {
						dataBase.executeUpdate("INSERT INTO books VALUES ('"+yearField.getText()+"', '"+titleField.getText()+"', '"+publisherField.getText()+
								"', '"+authorField.getText()+"', '"+isbnField.getText()+"')");
						messageAddBookButton.setText("Ksiazka zostala dodana pomyslnie");
						clear();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	        dataBase.disconnect();
	        }

	    });


	}

	public static boolean checkString(String text) {
		for(int i=0; i<text.length(); i++){
			if(text.charAt(i) < '0' || text.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}

	public void clear() {
		yearField.clear();
		titleField.clear();
		publisherField.clear();
		authorField.clear();
		isbnField.clear();
	}


}
