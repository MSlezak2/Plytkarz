package pl.dmms.plytkarz;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;

//TODO: To make possible running the app on the localhost without configuration data like email password or reCAPTCHA keys

@RestController
public class PlytkarzController {
    
    /**
     * Path to the folder containing images, relative to the root folder of the application
     */
    public static final String IMAGES_PATH_ROOT = "src\\main\\resources\\static\\css\\images\\";
    /**
     * Path to the folder containing images, relative to the 'static' folder
     */
    public static final String IMAGES_PATH_STATIC = "css\\images\\";
    
    @Autowired
    private MailSender mailSender;
    @Autowired
    private ReCaptchaProperties reCaptchaProperties;
    
    @GetMapping("/gallery")
    public ModelAndView getGallery(){
        ModelAndView modelAndView = new ModelAndView("gallery");
        
        // find images to be displayed
        File imagesDir = new File(IMAGES_PATH_ROOT);
        //TODO: what if there's no image in specified directory?
        String[] imagesNames = imagesDir.list();
        Path[] imagesPaths = new Path[imagesNames.length];
        for (int i = 0; i < imagesPaths.length; i++) {
            imagesPaths[i] = Path.of(IMAGES_PATH_STATIC + imagesNames[i]);
        }
        modelAndView.addObject("imagesPaths", imagesPaths);
        
        return modelAndView;
    }
    
    @GetMapping("/")
    public ModelAndView getIndex(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("mail",new Mail());
        
        return modelAndView;
    }
    
    
    @PostMapping("/")
    public ModelAndView sendEmail(@ModelAttribute @Valid Mail mail, BindingResult bindingResult, HttpServletRequest gRequest){
        // TODO: Do something for page to stay in the same place (contact form) after submitting
        ModelAndView modelAndView = new ModelAndView("index");
        
        //reCAPTCHA (//TODO: Maybe extract that into separate class?
        String responseToken = gRequest.getParameter("g-recaptcha-response");
    
        RestTemplate restTemplate = new RestTemplate();
        //TODO: Send data as a body instead of in URL
        String url = reCaptchaProperties.getUrl()+"?secret="+reCaptchaProperties.getSecretkey()+"&response="+responseToken;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        
        ResponseEntity<ReCaptchaResponse> templateExchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ReCaptchaResponse.class);
        ReCaptchaResponse reCaptchaResponse = templateExchange.getBody();
        
        
        if (!bindingResult.hasErrors() && reCaptchaResponse.isSuccess()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail.getRecipient());
            message.setText(mail.getMessageText());
            message.setFrom(mail.getSender());
            message.setSubject(mail.getSubject());
            try {
                mailSender.send(message);
                //TODO: Maybe some dialog about successful delivery?
            } catch (MailException me) {
                System.out.println(me.getMessage());
            }
        }
        
        //TODO: Exception handling (e.g. subclasses of org.springframework.mail.MailException)
        //TODO: Prevent situations like spamming somehow
        return modelAndView;
    }
    
    
}
