package ma.org.comfybackend.security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender emailSender;



    public void sendMessage(String to,String subject,String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zinebeljanyani@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);

    }
}
