import database.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) throws SQLException {
        DBConnection db = DBConnection.getInstance();
        Connection conn = db.getConnection();
        Statement statement = conn.createStatement();
            if(statement.execute("SELECT * FROM emails")){
                ResultSet rs;
                rs = statement.getResultSet();
                System.out.println("***1res1***");
                rs.next();
                int x = rs.getInt("STATUS");
                System.out.println(x);
            }else{
                System.out.println("else");
            }
    }
}
