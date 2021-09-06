import database.DBConnection;
import model.Email;
import model.EmailSender;

import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Program {

    static int THREADS = 10;

    public static void main(String[] args) {
        DBConnection db = DBConnection.getInstance();
        Connection conn = db.getConnection();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS);    // set x threads.
//        executor.submit(new EmailSender(1));     // and send the result set? or id?
//        executor.submit(new EmailSender(2));     // and send the result set? or id?


        // allways checking the table for new emails.
        while (true) {
            try {
                String getQuert = "SELECT * FROM emails e WHERE e.STATUS = 0";          // get all emails which are ready to be sent.
                try (Statement statement = conn.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(getQuert);
                    while (resultSet.next()) {
                        int emailId = resultSet.getInt("EMAIL_ID");
                        String sender = resultSet.getString("SENDER");
                        String receiver = resultSet.getString("RECEIVER");
                        String subject = resultSet.getString("SUBJECT");
                        String message = resultSet.getString("MESSAGE");
                        int status = resultSet.getInt("STATUS");
                        Email email = new Email(emailId, sender, receiver, subject, message, status);

                        // update to working status
                        String updateQuery = "update emails set STATUS = 1 where email_id = ?";
                        PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
                        preparedStatement.setInt(1,emailId);
                        preparedStatement.execute();

                        // start working (send the email)
                        executor.submit(new EmailSender(email));


                    }
                } catch (Exception e) {
                    System.out.println("Crashed at connection: " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Oops something went wrong.");
                System.out.println(e);
            }


        }


//
//        Statement statement = conn.createStatement();
//            if(statement.execute("SELECT * FROM emails")){
//                ResultSet rs;
//                rs = statement.getResultSet();
//                System.out.println("***1res1***");
//                rs.next();
//                int x = rs.getInt("STATUS");
//                System.out.println(x);
//            }else{
//                System.out.println("else");
//            }
    }
}
