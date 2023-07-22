package com.onrender.navkolodozvillya.auth;

import com.onrender.navkolodozvillya.config.JwtService;
import com.onrender.navkolodozvillya.config.TestDatabaseContainerConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestDatabaseContainerConfig.class)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
class AuthenticationServiceTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/city"))
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    public void shouldAllowAccessToAuthenticatedUsers() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/city")
                        .header("Authorization", "Bearer " + generateTestToken()))
                .andExpect(status().isOk());
    }

    private String generateTestToken() {
        // by registered user "oleh123"
        return jwtService
                .generateToken(userDetailsService
                        .loadUserByUsername("user@mail.com"));
    }
}