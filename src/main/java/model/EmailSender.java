package model;

import database.DBConnection;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.sql.PreparedStatement;
import java.util.Properties;



public class EmailSender implements Runnable{
    DBConnection db;
    int emailId;
    String sender;
    String receiver;
    String subject;
    String message;
    int status;

    final String myAccountEmail = "communicationltdproj@gmail.com";
    final String password = "Tomcat!234";


    public EmailSender(Email email){
         db = DBConnection.getInstance();
         emailId = email.getEmailId();
         sender = email.getSender();
         receiver = email.getReceiver();
         subject = email.getSubject();
         message = email.getMessage();
         status = email.getStatus();
    }

    @Override
    public void run() {
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.mailtrap.io");
            prop.put("mail.smtp.port", "25");
            prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");


            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password);
                }
            });


            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);
            String msg = this.message;
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println(" ***** Email sent successfully *****");


            // update email row to finised.
            String updateQuery = "update emails set STATUS = 2 where email_id = ?";
            PreparedStatement preparedStatement = db.getConnection().prepareStatement(updateQuery);
            preparedStatement.setInt(1,emailId);
            preparedStatement.execute();


            // the logic of the send email.
            // if done successfully, change status to 2 (sent successfully)
        }catch (Exception e){
            System.out.println(e);
        }
    }


}
