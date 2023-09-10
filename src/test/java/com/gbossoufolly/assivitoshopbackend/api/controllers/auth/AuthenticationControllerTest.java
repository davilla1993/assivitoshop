package com.gbossoufolly.assivitoshopbackend.api.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbossoufolly.assivitoshopbackend.api.models.RegistrationBody;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("testmail", "secret"))
            .withPerMethodLifecycle(true);

    @Test
    public void testRegisterUserSuccess() throws Exception {
        RegistrationBody registrationBody = new RegistrationBody();
        registrationBody.setUsername("UserC");
        registrationBody.setEmail("UserC@junit.com");
        registrationBody.setFirstName("UserC-FirstName");
        registrationBody.setLastName("UserC-LastName");
        registrationBody.setPassword("PasswordC-123");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(objectMapper.writeValueAsString(registrationBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testRegisterUserConflict() throws Exception {
        RegistrationBody registrationBody = new RegistrationBody();
        registrationBody.setUsername("UserA");
        registrationBody.setEmail("UserA@junit.com");
        registrationBody.setFirstName("UserA-FirstName");
        registrationBody.setLastName("UserA-LastName");
        registrationBody.setPassword("PasswordC-123");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(objectMapper.writeValueAsString(registrationBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void testRegisterUserInternalServerError() throws Exception {
        RegistrationBody registrationBody = new RegistrationBody();
        registrationBody.setUsername(null);
        registrationBody.setEmail("AuthenticationControllerTest$testRegister@junit.com");
        registrationBody.setFirstName("FirstName");
        registrationBody.setLastName("LastName");
        registrationBody.setPassword("Password123");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationBody)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}


