import java.util.Properties;


import java.io.IOException;
import java.lang.RuntimeException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
public class DatabaseConfig {
    private static final Properties properties= new Properties();
    // Static block runs ONCE when the class is loaded into memory
    static{
    try(InputStream input= DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")){
       if(input==null){
        throw new RuntimeException("Unable to find db.properties in resources folder");
       }
       // Load the properties file
       properties.load(input);
       // Explicitly register the MySQL driver class (Good practice in core JDBC)
       Class.forName("com.mysql.cj.jdbc.Driver");

    }
    catch(IOException e){
        throw new RuntimeException("Failed to load database configuration properties",e);
    }
    catch(ClassNotFoundException e){
        throw new RuntimeException("MySQL JDBC Driver not found in classpath",e);
    }
}
// Private constructor to prevent instantiation
private DatabaseConfig(){}
/**
     * Tries to establish and return a live connection to the MySQL container.
     * Note: The calling method is responsible for closing this connection 
     * (which we will handle using try-with-resources).
     */
public static Connection getConnection() throws SQLException{
   return DriverManager.getConnection(
    properties.getProperty("db.url"),
    properties.getProperty("db.username"),
    properties.getProperty("db.password")
   );
}

}
