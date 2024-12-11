package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClimberProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClimberProfileRepository climberProfileRepository;

    @Autowired
    private ClimberUserRepository climberUserRepository;

    List<ClimberUser> listUsers;

    @BeforeEach
    public void setupData() {
        // climberUser
        ClimberUser climberUser = new ClimberUser("CrisSharma", "cricri@gmail.com", "password1234", LocalDateTime.now(), LocalDateTime.now());
        climberUserRepository.saveAndFlush(climberUser);
        listUsers = climberUserRepository.findAll();

        //climberProfile
        ClimberProfile climberProfile = new ClimberProfile(1L, "CrisSharma", "path/image.png", 1L, 2L, climberUser, true, "Salut salut");
        climberProfileRepository.saveAndFlush(climberProfile);
    }

    @Test
    public void getClimberProfileByClimberUserId_unAuthenticated_unAuthorized() throws Exception {
        Long climberUserId = 1L;
        mockMvc.perform(get("/api/climber-profiles/climber-users/{climberUserId}", climberUserId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void getClimberProfileByClimberUserId_whenFindByUserId_thenSuccess() throws Exception {

        Long climberUserId = listUsers.getFirst().getId();

        mockMvc.perform(get("/api/climber-profiles/climber-users/" + climberUserId))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.avatar").value("path/image.png"))
                .andExpect(jsonPath("$.climberUserId").value(climberUserId));
    }

}
