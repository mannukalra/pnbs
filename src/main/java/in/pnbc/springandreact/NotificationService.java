package in.pnbc.springandreact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class NotificationService {

    @Autowired
    JavaMailSenderImpl javaMailSender;


    @Value("${spring.mail.password}")
    private String password;

    @Value( "${spring.mail.username}" )
    private String fromMailAddress;

    public void sendNotification(ClientDetails client) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(client.getMailId());
        mail.setFrom(fromMailAddress);
        mail.setSubject(client.firstName+" "+client.lastName+" wants to connect!");
        mail.setText(client.getMessage()+client.getMobileNumber());

        String actualPwd = new String(Base64.getDecoder().decode(password));
        javaMailSender.setPassword(actualPwd);
        javaMailSender.send(mail);
    }

}
