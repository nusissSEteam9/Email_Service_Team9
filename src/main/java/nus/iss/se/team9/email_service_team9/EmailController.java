package nus.iss.se.team9.email_service_team9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendEmail(emailDetails);
    }

    @PostMapping("/sendEmailOTP")
    public String sendEmailOTP(@RequestBody EmailDetails emailDetails) {
        return emailService.sendEmail(emailDetails);
    }
}
