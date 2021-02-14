package pl.dmms.plytkarz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;

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
    public ModelAndView sendEmail(@ModelAttribute @Valid Mail mail, BindingResult bindingResult){
        // TODO: Do something for page to stay in the same place (contact form) after submitting
        ModelAndView modelAndView = new ModelAndView("index");
    
        if (!bindingResult.hasErrors()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail.getRecipient());
            message.setText(mail.getMessageText());
            message.setFrom(mail.getSender());
            message.setSubject(mail.getSubject());
            try {
                mailSender.send(message);
            } catch (MailException me) {
                System.out.println(me.getMessage());
            }
        }
        
        //TODO: Exception handling (e.g. subclasses of org.springframework.mail.MailException)
        //TODO: Prevent situations like spamming somehow
        return modelAndView;
    }
    
    
}
