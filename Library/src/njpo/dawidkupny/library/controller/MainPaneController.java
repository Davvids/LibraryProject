package njpo.dawidkupny.library.controller;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainPaneController implements Initializable{

    @FXML
    private Button borrowButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button giveBackButton;

    @FXML
    private TextArea libraryTextArea;

    @FXML
    private TextArea borrowedTextArea;

    @FXML
    private AddBookController addBookController;

    @FXML
    private TextField borrowField;

    ResultSet rs;
    int counter = 1;
    int borrowCounter = 1;
    String a = null, b=null, c=null, d=null, f=null, g=null;

    DataBaseConnection dataBase = DataBaseConnection.getInstance();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//dataBase.connect();
		connect();

		try {
			rs = DataBaseConnection.getInstance().executeQuery("select * from books order by title asc");
			while(rs.next()) {
				libraryTextArea.setText(libraryTextArea.getText()+"\n"+counter+++". "+(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - "+rs.getString(5)));
			}
			rs = DataBaseConnection.getInstance().executeQuery("select * from borrowedBooks order by title asc");
			while(rs.next()) {
				borrowedTextArea.setText(borrowedTextArea.getText()+"\n"+borrowCounter+++". "+(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - "+rs.getString(5)));
			}
			refreshButton.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent event) {
		        	connect();
		        	libraryTextArea.clear();
		            int counter = 1;
		            try {
						rs = DataBaseConnection.getInstance().executeQuery("select * from books order by title asc");
						while(rs.next()) {
							libraryTextArea.setText(libraryTextArea.getText()+"\n"+counter+++". "+(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - "+rs.getString(5)));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		        disconnect();
		        }
		    });
		} catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();

		searchButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	connect();
	        	int serachCounter = 1;
	            libraryTextArea.clear();
	            if(searchField.getText().equals("") || searchField.getText().charAt(0) == ' '){
	            	libraryTextArea.setText("Nie podales tytulu.");
	            } else {
		            try {
						rs = DataBaseConnection.getInstance().executeQuery("SELECT * FROM books WHERE title LIKE '%"+searchField.getText()+"%'");
						while(rs.next()) {
							libraryTextArea.setText(libraryTextArea.getText()+"\n"+serachCounter+++". "+(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - "+rs.getString(5)));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            disconnect();
	        }
	    });

		addBookButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	Parent parent;
				try {
					parent = FXMLLoader.load(getClass().getResource("/njpo/dawidkupny/library/view/AddBookPane.fxml"));
					Stage stage = new Stage();
		    		Scene scene = new Scene(parent);
		    		stage.setScene(scene);
		    		stage.setTitle("Dodawanie Ksiazki");
		    		stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        }
	    });

		borrowButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	connect();
	        	if(borrowField.getText().equals("") || borrowField.getText().charAt(0) == ' '){
	            	libraryTextArea.setText("Nie podales tytulu.");
	            } else {
		            try {
						rs = DataBaseConnection.getInstance().executeQuery("SELECT * FROM books WHERE title LIKE '%"+borrowField.getText()+"%'");
						while(rs.next()) {
							a = rs.getString(1);
							b = rs.getString(2);
							c = rs.getString(3);
							d = rs.getString(4);
							f = rs.getString(5);
						}
							if(borrowField.getText().equals(b)) {
							  	dataBase.executeUpdate("INSERT INTO borrowedBooks VALUES ('"+a+"', '"+b+"', '"+c+
								"', '"+d+"', '"+f+"')");
								borrowedTextArea.setText(borrowedTextArea.getText()+"\n"+borrowCounter+++". "+a+" - "+b+" - "+c+" - "+d+" - "+f);
							}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        	disconnect();
	        	}
	    });

		giveBackButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	connect();
	        	int giveCounter = 1;
	        	if(borrowField.getText().equals("") || borrowField.getText().charAt(0) == ' '){
	            	libraryTextArea.setText("Nie podales tytulu.");
	        	} else {
		            try {
						rs = DataBaseConnection.getInstance().executeQuery("SELECT * FROM borrowedBooks WHERE title LIKE '%"+borrowField.getText()+"%'");
						while(rs.next()) {
							g = rs.getString(2);
						}
							if(borrowField.getText().equals(g)) {
							  	dataBase.executeUpdate("DELETE FROM borrowedBooks WHERE title = '"+g+"'");
							}
							borrowedTextArea.clear();
							rs = DataBaseConnection.getInstance().executeQuery("select * from borrowedBooks order by title asc");
							while(rs.next()) {
								borrowedTextArea.setText(borrowedTextArea.getText()+"\n"+giveCounter+++". "+(rs.getString(1)+" - "+rs.getString(2)+" - "+rs.getString(3)+" - "+rs.getString(4)+" - "+rs.getString(5)));
							}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	        	disconnect();
	        }
	    });

	}
	public void connect() {
		dataBase.connect();
	}

	public void disconnect() {
		dataBase.disconnect();
	}

}

