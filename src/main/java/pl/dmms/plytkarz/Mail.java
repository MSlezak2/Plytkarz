package pl.dmms.plytkarz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class Mail {
    
    //TODO: Try to externalize the recipients' email address source (in some configuration file for example)
    private final String RECIPIENT = "mkowaldwa@gmail.com";
    //TODO: Maybe some kind of validation here?
    private String sender;
    private String subject;
    private String messageText;
    
    public String getRecipient() {
        return RECIPIENT;
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getMessageText() {
        return messageText;
    }
    
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
