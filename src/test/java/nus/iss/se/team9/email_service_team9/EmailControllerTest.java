package nus.iss.se.team9.email_service_team9;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

    private AutoCloseable mocks; // 用于在测试完成后关闭

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this); // 初始化 mocks
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close(); // 关闭 mocks 以释放资源
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