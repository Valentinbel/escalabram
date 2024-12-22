package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.ClimberProfile;
import com.escalabram.escalabram.model.ClimberUser;
import com.escalabram.escalabram.repository.ClimberProfileRepository;
import com.escalabram.escalabram.repository.ClimberUserRepository;
import com.escalabram.escalabram.service.dto.ClimberProfileDTO;
import com.escalabram.escalabram.service.mapper.ClimberProfileMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Active le profil "test" défini dans application-test.properties
@Transactional
class ClimberProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Utilisé pour sérialiser/désérialiser JSON

    @Autowired
    private ClimberProfileRepository climberProfileRepository;

    @Autowired
    private ClimberUserRepository climberUserRepository;

    @Autowired
    private ClimberProfileMapper climberProfileMapper;

    private List<ClimberUser> listUsers;
    private List<ClimberProfile> listProfiles;

    @BeforeEach
    public void setupData() {
        // climberUser
        ClimberUser climberUser = new ClimberUser("CrisSharma", "cricri@gmail.com", "password1234", LocalDateTime.now(), LocalDateTime.now());
        this.climberUserRepository.saveAndFlush(climberUser);
        this.listUsers = climberUserRepository.findAll();

        //climberProfile
        ClimberProfile climberProfile = new ClimberProfile(1L, "CrisSharma", "path/image.png", 1L, 2L, climberUser, true, "Salut salut");
        this.climberProfileRepository.saveAndFlush(climberProfile);
        this.listProfiles = climberProfileRepository.findAll();
    }

    @Test
    void getClimberProfileByClimberUserId_unAuthenticated_unAuthorized() throws Exception {
        Long climberUserId = 1L;
        this.mockMvc.perform(get("/api/climber-profiles/climber-users/{climberUserId}", climberUserId))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getClimberProfileByClimberUserId_whenFindByUserId_thenSuccess() throws Exception {

        Long climberUserId = listUsers.getFirst().getId();

        this.mockMvc.perform(get("/api/climber-profiles/climber-users/" + climberUserId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.avatar").value("path/image.png"))
                .andExpect(jsonPath("$.climberUserId").value(climberUserId));
    }

    @Test
    @WithMockUser
    void getClimberProfileByClimberUserId_whenFindByUserId_thenNotFound() throws Exception {

        long climberUserIdNotFound = listUsers.getFirst().getId() + 1;
        this.mockMvc.perform(get("/api/climber-profiles/climber-users/" + climberUserIdNotFound))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void saveClimberProfile_climberProfileDto_created() throws Exception {

        ClimberUser climberUserToCreate = new ClimberUser("OriBertone", "ori@gmail.com", "password1234567", LocalDateTime.now(), LocalDateTime.now());
        climberUserRepository.saveAndFlush(climberUserToCreate);
        Long newclimberUserId = listUsers.getFirst().getId() +1;
        Long newprofileId = listProfiles.getFirst().getId() +1;
        ClimberProfileDTO profileDTO = new ClimberProfileDTO(newprofileId, "OrianeBertone", "image.png", 2L, 1L, true, "salut c'est Bertone", newclimberUserId);

        mockMvc.perform(post("/api/climber-profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileDTO)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) //MediaType.APPLICATION_JSON_VALUE
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.avatar").value("image.png"))
                .andExpect(jsonPath("$.climberUserId").value(newclimberUserId));
    }

    @Test
    @WithMockUser
    void saveClimberProfile_climberProfileDto_update() throws Exception {

        ClimberProfileDTO profileDTOToUpdate = climberProfileMapper.toClimberProfileDTO(listProfiles.getFirst());
        profileDTOToUpdate.setAvatar("/path/enfaituneautreimage.png");

        mockMvc.perform(post("/api/climber-profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileDTOToUpdate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.avatar").value("/path/enfaituneautreimage.png"))
                .andExpect(jsonPath("$.climberUserId").value(listProfiles.getFirst().getClimberUser().getId()));
    }
}
