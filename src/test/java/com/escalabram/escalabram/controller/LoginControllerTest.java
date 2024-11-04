//package com.escalabram.escalabram.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class LoginControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void shouldReturnDefaultMessage() throws Exception {
//        mockMvc.perform(get("/login"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

//    @Test
//    void userLoginTest() throws Exception {
//        mockMvc.perform(formLogin("/login")
//                        .user("dbuser").password("user")).
//                andExpect(authenticated());
//        // On pourra créer un faux compte pour les tests. Et le supprimer plus tard pour raison de sécu
//    }

//    @Test
//    void userLoginFailed() throws Exception {
//        mockMvc.perform(formLogin("/login")
//                        .user("user").password("WRONGPASSWORD"))
//                .andExpect(unauthenticated());
//    }

//    @Test
//    @WithMockUser
//    void shouldReturnUserPage() throws Exception {
//        mockMvc.perform(get("/user"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//}
