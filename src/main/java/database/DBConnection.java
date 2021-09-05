package database;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection = null;
    private static Connection dbConn = null;


    public DBConnection(){
        setDbConnection();
    }
    public Connection getConnection(){
        return dbConn;
    }
    public static DBConnection getInstance(){
        if(dbConnection == null)
            dbConnection = new DBConnection();
        return dbConnection;
    }
    public void setDbConnection(){
        String dbDriverClass;
        String dbConnUrl;
        String dbUserName;
        String dbPassword;
        try {
            Properties props = new Properties();
            String propFile = "src/main/resources/JDBCSettings.properties";
            FileReader fileReader = new FileReader(propFile);
            props.load(fileReader);
            dbDriverClass = props.getProperty("db.driver.class");
            dbConnUrl = props.getProperty("db.conn.url");
            dbUserName = props.getProperty("db.username");
            dbPassword = props.getProperty("db.password");


            Class.forName(dbDriverClass);
            dbConn = DriverManager.getConnection(dbConnUrl, dbUserName, dbPassword);

            /*   test   */
//            Statement statement = dbConn.createStatement();

//            if(statement.execute("SELECT * FROM emails")){
//                ResultSet rs;
//                rs = statement.getResultSet();
//                System.out.println("***res***");
//                rs.next();
//                int x = rs.getInt("STATUS");
//                System.out.println(x);
//            }else{
//                System.out.println("else");
//            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
