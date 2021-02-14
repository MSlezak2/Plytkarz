package pl.dmms.plytkarz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class Mail {
    
    private static final String INVALID_SENDER_ADDRESS_MSG = "Nieprawidłowy adres email";
    private static final String BLANK_FIELD_MSG = "Proszę uzupełnić";
    
    //TODO: Try to externalize the recipients' email address source (in some configuration file for example)
    @Email
    private final String RECIPIENT = "mkowaldwa@gmail.com";
    @Email(message = INVALID_SENDER_ADDRESS_MSG)
    @NotBlank(message = BLANK_FIELD_MSG)
    private String sender;
    @NotBlank(message = BLANK_FIELD_MSG)
    private String subject;
    @NotBlank(message = BLANK_FIELD_MSG)
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
