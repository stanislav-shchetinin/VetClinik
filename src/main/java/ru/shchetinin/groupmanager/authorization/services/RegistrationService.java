package ru.shchetinin.groupmanager.authorization.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.shchetinin.groupmanager.authorization.dao.AuthorityRepository;
import ru.shchetinin.groupmanager.repositories.UserRepository;
import ru.shchetinin.groupmanager.authorization.entities.Authority;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.authorization.roles.RoleAdd;
import ru.shchetinin.groupmanager.authorization.exceptions.ActivationCodeNotFoundException;
import ru.shchetinin.groupmanager.authorization.exceptions.UserAlreadyExistsException;
import ru.shchetinin.groupmanager.responses.Response;

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
            Authority authority = new Authority(user.getUsername(), RoleAdd.ROLE_USER.name());
            authorityRepo.save(authority);
            log.debug("AUTHORITY. username: {}, role: {}", authority.getUsername(), authority.getAuthority());
            sendEmail(user);
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