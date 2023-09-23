package com.gbossoufolly.assivitoshopbackend.api.security;

import com.gbossoufolly.assivitoshopbackend.models.LocalUser;
import com.gbossoufolly.assivitoshopbackend.repository.LocalUserRepository;
import com.gbossoufolly.assivitoshopbackend.services.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTRequestFilterTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private LocalUserRepository localUserRepository;
    private static final String AUTHENTICATED_PATH= "/auth/me";

    @Test
    public void testAuthenticatedRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHENTICATED_PATH))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testBadToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHENTICATED_PATH)
                .header("Authorization", "BadTokenThatIsNotValid"))
                .andExpect(status().isForbidden());
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHENTICATED_PATH)
                        .header("Authorization", "Bearer BadTokenThatIsNotValid"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUnverifiedUser() throws Exception {
        LocalUser user = localUserRepository.findByUsernameIgnoreCase("UserB").get();
        String token = jwtService.generateJWT(user);
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHENTICATED_PATH)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testSuccessful() throws Exception {
        LocalUser user = localUserRepository.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHENTICATED_PATH)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
