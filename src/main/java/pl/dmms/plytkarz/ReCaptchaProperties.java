package pl.dmms.plytkarz;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "recaptcha")
public class ReCaptchaProperties {
    
    private String url;
    private String secretkey;
    private String sitekey;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getSecretkey() {
        return secretkey;
    }
    
    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }
    
    public String getSitekey() {
        return sitekey;
    }
    
    public void setSitekey(String sitekey) {
        this.sitekey = sitekey;
    }
}
