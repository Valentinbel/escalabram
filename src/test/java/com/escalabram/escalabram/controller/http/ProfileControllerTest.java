package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.model.Profile;
import com.escalabram.escalabram.model.User;
import com.escalabram.escalabram.repository.ProfileRepository;
import com.escalabram.escalabram.repository.UserRepository;
import com.escalabram.escalabram.service.mapper.ProfileMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Active le profil "test" défini dans application-test.properties
@Transactional
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper; // Utilisé pour sérialiser/désérialiser JSON
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileMapper profileMapper;

    private List<User> listUsers;
    private List<Profile> listProfiles;

    @BeforeEach
    void setupData() {
        User user = User.builder()
                .userName("CrisSharma")
                .email("cricri@gmail.com")
                .password("password1234")
                .createdAt(LocalDateTime.now())
                .build();
        this.userRepository.saveAndFlush(user);
        this.listUsers = userRepository.findAll();

        Profile profile = Profile.builder()
                .genderId(1L)
                .languageId(2L)
                .isNotified(true)
                .user(user)
                .profileDescription("Salut salut")
                .build();
        this.profileRepository.saveAndFlush(profile);
        this.listProfiles = profileRepository.findAll();
    }

    @Test
    void getProfileByUserId_UnAuthenticated_UnAuthorized() throws Exception {
        Long userId = 1L;
        this.mockMvc.perform(get("/api/profiles/users/{userId}", userId))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getProfileByUserId_UserId_Success() throws Exception {

        Long userId = listUsers.getFirst().getId();

        this.mockMvc.perform(get("/api/profiles/users/" + userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.profileDescription").value("Salut salut"))
                .andExpect(jsonPath("$.userId").value(userId));
    }

    @Test
    @WithMockUser
    void getProfileByUserId_UserId_NotFound() throws Exception {

        long userIdNotFound = listUsers.getFirst().getId() + 1;
        this.mockMvc.perform(get("/api/profiles/users/" + userIdNotFound))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
// TODO
//    @Test
//    @WithMockUser
//    void saveProfile_ProfileDto_Created() throws Exception {
//
//        User userToCreate = new User("OriBertone", "ori@gmail.com", "password1234567", LocalDateTime.now(), LocalDateTime.now());
//        userRepository.saveAndFlush(userToCreate);
//        Long newUserId = listUsers.getFirst().getId() +1;
//        Long newprofileId = listProfiles.getFirst().getId() +1;
//        ProfileDTO profileDTO = new ProfileDTO(newprofileId, 3L, 2L, 1L, true, "salut c'est Bertone", newUserId);
//
//        mockMvc.perform(post("/api/profiles")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(profileDTO)))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) //MediaType.APPLICATION_JSON_VALUE
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.profileDescription").value("salut c'est Bertone"))
//                .andExpect(jsonPath("$.userId").value(newUserId))
//                .andExpect(jsonPath("$.fileInfoId").value(3L));
//    }

//    @Test
//    @WithMockUser
//    void saveProfile_ProfileDto_Update() throws Exception {
//
//        ProfileDTO profileDTOToUpdate = ProfileMapper.toProfileDTO(listProfiles.getFirst());
//
//        mockMvc.perform(post("/api/profiles")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(profileDTOToUpdate)))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(jsonPath("$.profileDescription").value("Salut salut"))
//                .andExpect(jsonPath("$.userId").value(listProfiles.getFirst().getUser().getId()));
//    }
}
