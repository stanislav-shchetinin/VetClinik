package ru.shchetinin.groupmanager.authorization.services;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shchetinin.groupmanager.repositories.UserRepository;
import ru.shchetinin.groupmanager.authorization.dto.JwtRequest;
import ru.shchetinin.groupmanager.authorization.dto.JwtResponse;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.authorization.exceptions.UserIsNotActiveException;
import ru.shchetinin.groupmanager.authorization.services.AuthService;
import ru.shchetinin.groupmanager.authorization.services.UserService;
import ru.shchetinin.groupmanager.authorization.utils.JwtTokenUtils;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthServiceTest {

    @InjectMocks
    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenUtils jwtTokenUtils;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private final JwtRequest authRequest = new JwtRequest("Name", "Password");

    @Test
    public void getUser_invalidName_returnUsernameNotFoundException() {
        when(userRepository.findByUsername(authRequest.getUsername()))
                .thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
                () -> authService.createAuthToken(authRequest));
    }

    @Test
    public void getUser_invalidPassword_returnUsernameNotFoundException() {
        User user = new User();
        user.setPassword("NotPassword");
        when(userRepository.findByUsername(authRequest.getUsername()))
                .thenReturn(user);
        when(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())).thenReturn(false);
        assertThrows(UsernameNotFoundException.class,
                () -> authService.createAuthToken(authRequest));
    }

    @Test
    public void getUser_isNotActive_returnUserIsNotActiveException() {
        User user = mock(User.class);
        user.setPassword("Password");
        when(userRepository.findByUsername(authRequest.getUsername()))
                .thenReturn(user);
        when(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(user.isEnabled()).thenReturn(false);
        assertThrows(UserIsNotActiveException.class,
                () -> authService.createAuthToken(authRequest));
    }

    @Test
    public void getUser_validUser_returnValidToken() {
        User user = mock(User.class);
        user.setPassword("NotPassword");
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                authRequest.getUsername(),
                authRequest.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(userRepository.findByUsername(authRequest.getUsername()))
                .thenReturn(user);
        when(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(user.isEnabled()).thenReturn(true);
        when(userService.loadUserByUsername(authRequest.getUsername()))
                .thenReturn(userDetails);

        ResponseEntity<JwtResponse> response = authService.createAuthToken(authRequest);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().getToken(), new JwtResponse(jwtTokenUtils.generateToken(userDetails)).getToken());
    }
}
