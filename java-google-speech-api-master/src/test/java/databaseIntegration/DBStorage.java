package databaseIntegration;
import java.sql.*;
import java.util.Properties;


public class DBStorage {



	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "mohtasim70";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "students";
	// static final String DB_URL = "jdbc:mysql://localhost/students";
	/** The name of the table we are testing with */
	private final String tableNameS = "songs123";
	private final String tableNameA = "albums123";
	private final String tableNameArt = "artists123";
	private final String tableUser="UserInfo";

	private Statement stmt;

	/**
	 * Get a new database connection
	 *
	 * @return
	 * @throws SQLException
	 */

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);
		System.out.println("trying to get connection!! ");
		conn = DriverManager.getConnection("jdbc:mysql://"
		+ this.serverName + ":" + this.portNumber + "/"
		+ this.dbName,connectionProps);
		System.out.println(" Connection achieved!! ");
		return conn;
	}


	public boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}

	public void MakeDB(Connection conn)
	{
		stmt = null;
		String sql = "CREATE DATABASE students";
	      try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteAll(Connection conn)
	{
		Statement stmt=null;

			try {
				stmt=conn.createStatement();
				String sql = "delete  FROM Sometable";
			int a=	stmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}
	public void SelectAll(Connection conn)
	{
		Statement stmt=null;
		try {
			stmt=conn.createStatement();
			String sql = "SELECT * FROM Sometable";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("hello");
			while(rs.next()){
				int id  = rs.getInt("ID");

		         String name = rs.getString("NAME");
		         String food = rs.getString("food");
		         System.out.print("ID: " + id);
		         System.out.print(", name: " + name);
		         System.out.println(", food: " + food);

		      }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static boolean tableExist(Connection conn, String tableName) throws SQLException {
	    boolean tExists = false;
	    try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
	        while (rs.next()) {
	            String tName = rs.getString("TABLE_NAME");
	            if (tName != null && tName.equals(tableName)) {
	                tExists = true;
	                break;
	            }
	        }
	    }
	    return tExists;
	}

	public void run() throws ClassNotFoundException, SQLException {

		// Connect to MySQL
	//	MakeDB();
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("connection name is :: "+conn.getClass().getName());
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}





		// Create a table
		try {
			if(tableExist(conn,tableUser)==false)
			{
				String createString =
				        "CREATE TABLE " + this.tableUser + " ( " +
				        "USERNAME VARCHAR(40) UNIQUE NOT NULL, " +
				        "PASSWORD VARCHAR(40) NOT NULL, " +
				        "STATUS VARCHAR(40), " +
				        "ID INTEGER NOT NULL AUTO_INCREMENT, " +
				        "PRIMARY KEY (ID))"
				        ;

				this.executeUpdate(conn, createString);
				System.out.print("Table name: " + this.tableUser);

			}





//			if(tableExist(conn,tableNameA)==false)
//			{
//				String createString =
//				        "CREATE TABLE " + this.tableNameA + " ( " +
//				        "TITLE VARCHAR(40) NOT NULL , " +
//				        "PRIMARY KEY(TITLE))"
//				        ;
//
//				this.executeUpdate(conn, createString);
//				System.out.print("Table name: " + this.tableNameA);
//
//			}
//			if(tableExist(conn,tableNameS)==false)
//			{
//				String createString =
//				        "CREATE TABLE " + this.tableNameS + " ( " +
//				        "PATH VARCHAR(40) NOT NULL, " +
//				        "ALBUM_TITLE VARCHAR(40), " +
//				        "TITLE VARCHAR(40), " +
//				        "DURATION INTEGER, " +
//				        "TRACKNO INTEGER, " +
//				        "PRIMARY KEY (PATH,ALBUM_TITLE), " +
//				        "FOREIGN KEY (ALBUM_TITLE) " +
//				        "REFERENCES ALBUMS123(TITLE) "+
//				        "ON UPDATE CASCADE)"
//				        ;
//
//				this.executeUpdate(conn, createString);
//				System.out.print("Table name: " + this.tableNameS);
//
//			}
//			if(tableExist(conn,tableNameArt)==false)
//			{
//				String createString =
//				        "CREATE TABLE " + this.tableNameArt + " ( " +
//				        "NAME VARCHAR(40) NOT NULL ,"
//				        +"SONG_PATH VARCHAR(40), " +
//				        "PRIMARY KEY(NAME,SONG_PATH), " +
//				        "FOREIGN KEY (SONG_PATH) " +
//				        "REFERENCES SONGS123(PATH))"
//				        ;
//
//				this.executeUpdate(conn, createString);
//				System.out.print("Table name: " + this.tableNameArt);
//
//			}
			conn.close();


	    } catch (Exception e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}

	}

	public boolean register_User(String name,String pass) throws SQLException
	{
		Connection conn = null;
		boolean unique = true;
		String val=name;
		try {
			conn = this.getConnection();
			System.out.println("connection name is :: "+conn.getClass().getName());
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return false;
		}

		Statement statement = conn.createStatement() ;
	    ResultSet resultset = statement.executeQuery("SELECT * FROM students.UserInfo") ;
	    while(resultset.next()){
	        if(resultset.getString("USERNAME").equalsIgnoreCase(name)){
	          //  throw new SQLException("Duplicate info<br> Name " + nam);
	            unique = false;
	        }
	    }
		if(unique)
		{
		String input2="INSERT INTO UserInfo(USERNAME,PASSWORD,STATUS) "
				+"VALUES(?,?,?)";
		 PreparedStatement preparedStmt = conn.prepareStatement(input2);
		 preparedStmt.setString(1, name);
		 preparedStmt.setString(2, pass);
		 preparedStmt.setString(3, "ONLINE");
		 preparedStmt.execute();
		 conn.close();
		 return true;
			//	this.executeUpdate(conn, input2);
		}
		else
		{
			 conn.close();
			System.out.println("ERROR: Duplicate value of primary key");
			// throw new SQLException("Duplicate info<br> Username " + name);

			return false;
		}




	}

	public boolean login_User(String name,String pass) throws SQLException
	{
		Connection conn = null;
		boolean unique = true;
		String val=name;
		try {
			conn = this.getConnection();
			System.out.println("connection name is :: "+conn.getClass().getName());
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return false;
		}

		//Statement statement = conn.createStatement() ;
		String input2="SELECT USERNAME,PASSWORD FROM students.UserInfo where USERNAME=? AND PASSWORD=?";
	    PreparedStatement stmt = conn.prepareStatement(input2);
	    stmt.setString(1, name);
	    stmt.setString(2, pass);
	    ResultSet resultset =stmt.executeQuery();
	   // stmt.
	    while(resultset.next()){
	    	System.out.println(resultset.getString("USERNAME") + resultset.getString("PASSWORD"));
	        if(resultset.getString("USERNAME").equalsIgnoreCase(name) && resultset.getString("PASSWORD").equalsIgnoreCase(pass)){
	           return true;
	        }
	    }

		return false;
	}




	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DBStorage app = new DBStorage ();

		app.run();
//		app.register_User("user3ee", "Hello world");
//		System.out.println(app.login_User("user1", "Hello world"));

		//app.Remove_Song("C:/1.mp3");
}

}

