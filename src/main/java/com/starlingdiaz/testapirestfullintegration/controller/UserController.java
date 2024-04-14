/**
 * @author Starling Diaz on 4/11/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/11/2024.
 */

package com.starlingdiaz.testapirestfullintegration.controller;

import com.starlingdiaz.testapirestfullintegration.dto.LoginDto;
import com.starlingdiaz.testapirestfullintegration.dto.UserDto;
import com.starlingdiaz.testapirestfullintegration.dto.response.LoginResponseDto;
import com.starlingdiaz.testapirestfullintegration.dto.response.UserResponseDto;
import com.starlingdiaz.testapirestfullintegration.exception.UserAlreadyExistsException;
import com.starlingdiaz.testapirestfullintegration.service.IUserService;
import com.starlingdiaz.testapirestfullintegration.service.JwTService;
import com.starlingdiaz.testapirestfullintegration.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Manejo de usuarios")
public class UserController {

    private final IUserService userService;


    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user and provides a JWT for authentication.")@ApiResponses(   value = {
    @ApiResponse( responseCode = "201", description = "Usuario registrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class), examples = @ExampleObject(  name = "UserSuccessResponse", value = "{\"id\": \"d1de2fc4-8395-4942-9e5d-8887f2fd06e5\", \"created\": \"2024-04-13T00:09:22.2176616\", \"modified\": \"2024-04-13T00:09:22.2177783\", \"lastLogin\": \"2024-04-13T00:09:22.2177783\", \"token\": \"GH8HE9\", \"active\": true}"))),
    @ApiResponse(responseCode = "400", description = "Correo ya registrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "UserExists", value = "{\"mensaje\": \"Correo ya registrado\"}"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "ServerError", value = "{\"mensaje\": \"Error interno del servidor\"}")))
    })
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDto userDto) throws Exception {
        UserResponseDto userResponse = userService.registerUser(userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and issues a JWT.")@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuario logueado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class), examples = @ExampleObject(name = "LoginSuccess", value = "{\"token\": \"eyJhei...\", \"type\": \"Bearer\"}", summary = "Login exitoso"))),
    @ApiResponse(responseCode = "401", description = "Credenciales incorrectas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "CredentialsError", value = "{\"mensaje\": \"Credenciales incorrectas\"}"))),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "UserNotFound", value = "{\"mensaje\": \"Usuario no encontrado\"}"))),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = @ExampleObject(name = "ServerError", value = "{\"mensaje\": \"Error interno del servidor\"}")))
    })
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.loginAuthenticate(loginDto));
    }

}
