package pl.dmms.plytkarz;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.cef.handler.CefLoadHandler;

import java.sql.Timestamp;

public class ReCaptchaResponse {
    
    private boolean success;
    private double score;
    private String action;
    private Timestamp challenge_ts;
    private String hostname;
    @JsonProperty("error-codes")
    private String[] errors;
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public double getScore() {
        return score;
    }
    
    public void setScore(double score) {
        this.score = score;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public Timestamp getChallenge_ts() {
        return challenge_ts;
    }
    
    public void setChallenge_ts(Timestamp challenge_ts) {
        this.challenge_ts = challenge_ts;
    }
    
    public String getHostname() {
        return hostname;
    }
    
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
    public String[] getErrors() {
        return errors;
    }
    
    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}
