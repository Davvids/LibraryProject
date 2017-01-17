package njpo.dawidkupny.library.controller;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DataBaseConnection {

	private static DataBaseConnection instance;

	public static DataBaseConnection getInstance() {
		if(instance == null) {
			instance = new DataBaseConnection();
		}
		return instance;
	}

	final String DRIVER = "com.mysql.jdbc.Driver";
	final String DB_URL = "jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false";
	final String USER = "root";
	final String PASSWORD = "root";

	private Connection connection;
	private Statement stmt;
	private ResultSet rs;

	public Connection connect() {
		if(connection == null) {
			try {
			Class.forName(DRIVER);
			connection = (Connection) DriverManager.getConnection(DB_URL, USER, PASSWORD);

			stmt = (Statement) connection.createStatement();
			} catch(SQLException se) {
				System.err.println("Blad bazy danych");
			} catch(Exception e) {
				System.err.println("Blad");
			}
		}
		return connection;
	}

	public void disconnect() {
		if(connection != null) {
			try {
				connection.close();
				rs.close();
				stmt.close();
				connection = null;
				System.out.println("rozlaczono");
			} catch(SQLException e) {
				System.err.println("Blad rozlaczania");
			}
		}
	}

	public ResultSet executeQuery(String query) throws SQLException {

		return rs = stmt.executeQuery(query);
	}

	public int executeUpdate(String query) throws SQLException {
		System.out.println("Dodano rekod");
		return stmt.executeUpdate(query);
	}
}
