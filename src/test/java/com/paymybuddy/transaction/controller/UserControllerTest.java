package com.paymybuddy.transaction.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.paymybuddy.transaction.configuration.SecurityConfig;
import com.paymybuddy.transaction.services.ITransferService;
import com.paymybuddy.transaction.services.IUserService;
import com.paymybuddy.transaction.services.JpaUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * This class test the methods of the class UserController
 */
@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  JpaUserDetailsService jpaUserDetailsService;

  @MockBean
  IUserService userService;

  @MockBean
  ITransferService transferService;

  @MockBean
  UserController userController;

  @MockBean
  CommandLineRunner runner;

  //ok index
  @WithMockUser(username = "duke")
  @Test
  void shouldAllowAccessForAuthenticatedUser() throws Exception {
    this.mockMvc
        .perform(get("/index"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(content().string(containsString("My Money")))
    ;
  }


  // ok userform
  @Test
  void shouldAllowAccessForAnonymousUserToUserform() throws Exception {
    this.mockMvc
        .perform(get("/userform"))
        .andExpect(status().isOk())
        .andExpect(view().name("userform"))
        .andExpect(content().string(containsString("Create account")))
    ;
  }

  // ok contact
  @WithMockUser(username = "duke")
  @Test
  void shouldAllowAccessTOContact() throws Exception {
    this.mockMvc
        .perform(get("/contact"))
        .andExpect(status().isOk())
        .andExpect(view().name("contact"))
        .andExpect(content().string(containsString("My Buddies")))
    ;
  }

  // ok profile
  @WithMockUser(username = "duke")
  @Test
  void shouldAllowAccessToProfile() throws Exception {
    this.mockMvc
        .perform(get("/profile"))
        .andExpect(status().isOk())
        .andExpect(view().name("profile"))
        .andExpect(content().string(containsString("My profile")))
    ;
  }
}