package nus.iss.se.team9.email_service_team9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDetails emailDetails) {
        return emailService.sendEmail(emailDetails);
    }

    @PostMapping("/sendEmailOTP")
    public ResponseEntity<String> sendEmailOTP(@RequestBody EmailDetails emailDetails) {
        return emailService.sendEmail(emailDetails);
    }

    @GetMapping("/health")
    public String Healthy() {
        return "Email port is healthy";
    }
}
