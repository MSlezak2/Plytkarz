package pl.dmms.plytkarz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
    
    @GetMapping("/email")
    public boolean sendEmail(){
        boolean success = false;
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("mkowaldwa@gmail.com");
        message.setText("Sample text");
        message.setFrom("mkowaldwa@gmail.com");
        message.setSubject("Plytkarz - test 1");
        try {
            mailSender.send(message);
            success = true;
        } catch (MailException me) {
            System.out.println(me.getMessage());
        }
        
        //TODO: Exception handling (e.g. subclasses of org.springframework.mail.MailException)
        //TODO: Prevent situations like spamming somehow
        return success;
    }
    
    
}
