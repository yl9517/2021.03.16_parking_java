package DBconnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBhandle extends config{
	Connection dbConnection;
	
	public Connection getConnnection() {
		String connectionString = "jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname+"?serverTimezone = UTC&useSSL=false";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (Exception e) {
		}
		
		try {
			dbConnection = DriverManager.getConnection(connectionString,dbuser,dbpass);
		}catch (Exception e) {
		}
		
		return dbConnection;
	}

}
