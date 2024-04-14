/**
 * @author Starling Diaz on 4/12/2024.
 * @Academy StarAcademy
 * @version test-api-restfull-integration 1.0
 * @since 4/12/2024.
 */

package com.starlingdiaz.testapirestfullintegration.service.impl;

import com.starlingdiaz.testapirestfullintegration.dto.LoginDto;
import com.starlingdiaz.testapirestfullintegration.dto.UserDto;
import com.starlingdiaz.testapirestfullintegration.dto.response.LoginResponseDto;
import com.starlingdiaz.testapirestfullintegration.dto.response.UserResponseDto;
import com.starlingdiaz.testapirestfullintegration.entity.Phone;
import com.starlingdiaz.testapirestfullintegration.entity.Token;
import com.starlingdiaz.testapirestfullintegration.entity.User;
import com.starlingdiaz.testapirestfullintegration.exception.UserAlreadyExistsException;
import com.starlingdiaz.testapirestfullintegration.repository.TokenRepository;
import com.starlingdiaz.testapirestfullintegration.repository.UserRepository;
import com.starlingdiaz.testapirestfullintegration.service.IUserService;
import com.starlingdiaz.testapirestfullintegration.service.JwTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwTService jwTService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           TokenRepository tokenRepository, JwTService jwTService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwTService = jwTService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserDto userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("El correo ya esta registrado");
        }
        User user = getUser(userDto);
        userRepository.save(user);  // Save the user
        userRepository.flush();
        Set<Phone> phoneSet = getPhones(userDto, user);

        user.setPhones(phoneSet);
        userRepository.saveAndFlush(user);

        // Generate and save the token
        String token = generateAndSaveActivationToken(user);
        user.setToken(token);
        // Save user with the token
        userRepository.save(user);

        return new UserResponseDto(user);
    }
    private static Set<Phone> getPhones(UserDto userDto, User user) {
        Set<Phone> phoneSet = userDto.getPhones().stream()
                .map(phoneDto -> {
                    Phone phone = new Phone();
                    phone.setNumber(phoneDto.getNumber());
                    phone.setCitycode(phoneDto.getCitycode());
                    phone.setCountrycode(phoneDto.getCountrycode());
                    phone.setUser(user);
                    return phone;
                }).collect(Collectors.toSet());
        return phoneSet;
    }

    private User getUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);
        return user;
    }

    @Override
    public String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(45))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    public String generateActivationCode(int length) {
        String characters = "ABCDEFGHIJ89";
        StringBuilder resultBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            resultBuilder.append(characters.charAt(randomIndex));
        }
        return resultBuilder.toString();
    }
    @Override
    public LoginResponseDto loginAuthenticate(LoginDto loginDto) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = (User) auth.getPrincipal();
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("id", user.getId());
        var jwtToken = jwTService.generateToken(claims, user);
        return LoginResponseDto.builder()
                .token(jwtToken)
                .build();
    }

}