package nus.iss.se.team9.email_service_team9;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmailController.class)
class EmailControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @BeforeEach
    void setUp() {
        try (MockedStatic<MockitoAnnotations> mocked = Mockito.mockStatic(MockitoAnnotations.class)) {
            mocked.when(() -> MockitoAnnotations.openMocks(this)).thenReturn(null);
            mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
        }
    }

    @Test
    void testSendEmail() throws Exception {
        EmailDetails emailDetails = new EmailDetails(); // Populate fields as necessary
        String emailDetailsJson = new ObjectMapper().writeValueAsString(emailDetails);
        when(emailService.sendEmail(any(EmailDetails.class)))
                .thenReturn(new ResponseEntity<>("Email sent successfully", HttpStatus.OK));

        mockMvc.perform(post("/email/sendEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailDetailsJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Email sent successfully"));
    }

    @Test
    void testSendEmailOTP() throws Exception {
        EmailDetails emailDetails = new EmailDetails(); // Populate fields as necessary
        String emailDetailsJson = new ObjectMapper().writeValueAsString(emailDetails);
        when(emailService.sendEmail(any(EmailDetails.class)))
                .thenReturn(new ResponseEntity<>("OTP sent successfully", HttpStatus.OK));

        mockMvc.perform(post("/email/sendEmailOTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emailDetailsJson))
                .andExpect(status().isOk())
                .andExpect(content().string("OTP sent successfully"));
    }

    @Test
    void testHealthy() throws Exception {
        mockMvc.perform(get("/email/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Email port is healthy"));
    }
}