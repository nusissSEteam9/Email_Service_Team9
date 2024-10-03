package nus.iss.se.team9.email_service_team9;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final String username = "zhangten0131@gmail.com"; // 发送邮件的邮箱地址
    private final String password = "c o s f uvao ofrk etnj"; // 邮箱密码或授权码

    public String sendEmail(EmailDetails emailDetails) {
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

            return "Email sent successfully";
        } catch (Exception e) {
            return "Error while sending email: " + e.getMessage();
        }
    }
}
