package ru.shchetinin.vetclinik.authorization.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.shchetinin.vetclinik.authorization.dao.AuthorityRepository;
import ru.shchetinin.vetclinik.entities.Clinic;
import ru.shchetinin.vetclinik.repositories.ClinicRepository;
import ru.shchetinin.vetclinik.repositories.UserRepository;
import ru.shchetinin.vetclinik.authorization.entities.Authority;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.authorization.roles.RoleAdd;
import ru.shchetinin.vetclinik.authorization.exceptions.ActivationCodeNotFoundException;
import ru.shchetinin.vetclinik.authorization.exceptions.UserAlreadyExistsException;
import ru.shchetinin.vetclinik.responses.Response;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableAsync
@Slf4j
public class RegistrationService {

    private final UserRepository userRepo;
    private final AuthorityRepository authorityRepo;
    private final PasswordEncoder encoder;
    private final EmailSender emailSender;
    private final ClinicRepository clinicRepository;

    public Response addNewUser(User user) throws UserAlreadyExistsException {
        String login = user.getUsername();
        boolean isUserAlreadyExist = userRepo.findByUsername(login) != null;
        log.debug("login: {}, isUserAlreadyExist: {}", login, isUserAlreadyExist);
        if (isUserAlreadyExist){
            throw new UserAlreadyExistsException();
        } else {
            user.setEnabled(false);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setActivationCode(UUID.randomUUID().toString());
            userRepo.save(user);
            log.debug("USER. enabled: {}, password: {}", user.isEnabled(), user.getPassword());
            Authority authority = new Authority(user.getUsername(), String.valueOf(user.getRole()));
            authorityRepo.save(authority);
            log.debug("AUTHORITY. username: {}, role: {}", authority.getUsername(), authority.getAuthority());
            sendEmail(user);
            if (user.getRole().equals(RoleAdd.ROLE_CLINIC)) {
                Clinic clinic = new Clinic();
                clinic.setUser(user);
                clinicRepository.save(clinic);
            }
            return new Response(HttpStatus.OK.value(), "User successfully added");
        }
    }

    @Async
    public void sendEmail(User user){
        String email = user.getUsername();
        if (StringUtils.hasText(email)){
            String link = String.format("http://localhost:8080/activation/%s", user.getActivationCode());
            String message = String.format(
                    "Hello, %s!\n Welcome to GroupManager. Please, visit next link:%s", user.getUsername(), link
            );
            emailSender.send(email, "Activation Code", message);
        }
    }

    public void activation(String activationCode) {
        User user = userRepo.findByActivationCode(activationCode);

        log.debug(String.valueOf(user == null));

        if (user == null){
            throw new ActivationCodeNotFoundException();
        }
        user.setEnabled(true);
        user.setActivationCode(null);
        userRepo.save(user);
    }
}