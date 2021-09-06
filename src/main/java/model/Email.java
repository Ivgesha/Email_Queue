package model;

public class Email {

    int emailId;
    String sender;
    String receiver;
    String subject;
    String message;
    int status;

    public Email(int emailId, String sender, String receiver, String subject, String message, int status) {
        this.emailId = emailId;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
        this.status = status;
    }

    public int getEmailId(){
        return emailId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Email{" +
                "emailId=" + emailId +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
