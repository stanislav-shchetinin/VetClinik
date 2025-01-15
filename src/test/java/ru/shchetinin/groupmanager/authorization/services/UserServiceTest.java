package ru.shchetinin.groupmanager.authorization.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shchetinin.groupmanager.authorization.dao.AuthorityRepository;
import ru.shchetinin.groupmanager.repositories.UserRepository;
import ru.shchetinin.groupmanager.authorization.entities.Authority;
import ru.shchetinin.groupmanager.entities.User;
import ru.shchetinin.groupmanager.authorization.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthorityRepository authorityRepository;

    @Test
    public void getUsername_invalidName_returnUsernameNotFoundException() {
        User user = new User("Name", "Password",
                "ActivationCode", true);
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(user.getUsername()));
    }

    @Test
    public void getUsername_validName_returnOk() {
        User user = new User("Name", "Password",
                "ActivationCode", true);
        Authority authority = new Authority(user.getUsername(), "ROLE_USER");
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(user);
        when(authorityRepository.findByUsername(user.getUsername()))
                .thenReturn(authority);
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        assertThat(userDetails).isNotNull();
    }

}
