package nus.iss.se.team9.email_service_team9;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    private final String username = "zhangten0131@gmail.com"; // 发送邮件的邮箱地址
    private final String password = "c o s f uvao ofrk etnj"; // 邮箱密码或授权码

    @GetMapping("/health")
    public String checkHealth(){
        return "API is connected";
    }

    public ResponseEntity<String> sendEmail(EmailDetails emailDetails) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // sender
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDetails.getTo()));
            message.setSubject(emailDetails.getSubject()); // subject
            message.setText(emailDetails.getBody()); // body

            Transport.send(message); // send

            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while sending email: " + e.getMessage());
        }
    }
}
