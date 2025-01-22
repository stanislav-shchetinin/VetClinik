package ru.shchetinin.vetclinik.authorization.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.shchetinin.vetclinik.repositories.UserRepository;
import ru.shchetinin.vetclinik.authorization.dto.JwtRequest;
import ru.shchetinin.vetclinik.authorization.dto.JwtResponse;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.authorization.exceptions.UserIsNotActiveException;
import ru.shchetinin.vetclinik.authorization.utils.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest){

        var user = userRepository.findByUsername(authRequest.getUsername());
        if (user.isEmpty() ||
                !passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())){
            throw new UsernameNotFoundException("Uncorrected username or password");
        }
        if (!user.get().isEnabled()){
            throw new UserIsNotActiveException("User's email is not active");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, user.get().getRole()));
    }
}
