/**
 * @author Starling Diaz on 4/13/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/13/2024.
 */

package com.starlingdiaz.testapirestfullintegration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starlingdiaz.testapirestfullintegration.config.SecurityConfigTest;
import com.starlingdiaz.testapirestfullintegration.dto.PhoneDto;
import com.starlingdiaz.testapirestfullintegration.dto.UserDto;
import com.starlingdiaz.testapirestfullintegration.dto.response.UserResponseDto;
import com.starlingdiaz.testapirestfullintegration.exception.UserAlreadyExistsException;
import com.starlingdiaz.testapirestfullintegration.service.IUserService;
import com.starlingdiaz.testapirestfullintegration.service.JwTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(UserController.class)
@Import(SecurityConfigTest.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .defaultRequest(MockMvcRequestBuilders.get("/")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .build();
    }



    // Scenario 1 de caso de prueba positiva para el registro de usuario
    @Test
    public void registerUser_Success() throws Exception {
        UserDto userDto = UserDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("Pass1234")
                .phones(Collections.singleton(new PhoneDto("1234567", "1", "57")))
                .build();
        String userDtoJson = new ObjectMapper().writeValueAsString(userDto);

        UUID userId = UUID.randomUUID();
        UserResponseDto responseDto = new UserResponseDto(userId, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), "someToken123", true);

        given(userService.registerUser(any(UserDto.class))).willReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userId.toString()));
    }


    // Scenario 2 de caso de prueba negativa para el registro de usuario cuando el usuario ya existe
    @Test
    public void registerUser_UserAlreadyExists() throws Exception {
        UserDto userDto = UserDto.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .password("Pass1234")
                .phones(Collections.singleton(new PhoneDto("12345678", "1", "57")))
                .build();
        String userDtoJson = new ObjectMapper().writeValueAsString(userDto);

        given(userService.registerUser(any(UserDto.class)))
                .willThrow(new UserAlreadyExistsException("El correo ya registrado"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
                        .with(csrf()))  // Include CSRF token
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("El correo ya registrado")));
    }
}