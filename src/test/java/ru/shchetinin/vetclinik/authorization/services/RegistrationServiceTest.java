package ru.shchetinin.vetclinik.authorization.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shchetinin.vetclinik.authorization.dao.AuthorityRepository;
import ru.shchetinin.vetclinik.repositories.UserRepository;
import ru.shchetinin.vetclinik.entities.User;
import ru.shchetinin.vetclinik.authorization.exceptions.ActivationCodeNotFoundException;
import ru.shchetinin.vetclinik.authorization.exceptions.UserAlreadyExistsException;
import ru.shchetinin.vetclinik.responses.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegistrationServiceTest {

    @InjectMocks
    @Autowired
    private RegistrationService registrationService;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private AuthorityRepository authorityRepo;

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private EmailSender emailSender;

    private final User user = new User("Name", "Password", "activationCode", true);

    @Test
    public void getUser_invalidName_returnUserAlreadyExistsException() {
        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        assertThrows(UserAlreadyExistsException.class, () -> registrationService.addNewUser(user));
    }
    @Test
    public void getUser_validUser_returnOk() {
        when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
        Response response = registrationService.addNewUser(user);
        verify(emailSender, times(1)).send(eq(user.getUsername()), eq("Activation Code"), anyString());
        verify(userRepo, times(1)).save(user);
        verify(authorityRepo, times(1)).save(any());
        assertEquals(response.getCodeStatus(), 200);
    }

    @Test
    public void getUser_emptyEmail_returnOkSendMailZeroTime() {
        when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
        user.setUsername("");
        Response response = registrationService.addNewUser(user);
        verify(emailSender, times(0)).send(anyString(), anyString(), anyString());
        verify(userRepo, times(1)).save(user);
        verify(authorityRepo, times(1)).save(any());
        user.setUsername("Name");
        assertEquals(response.getCodeStatus(), 200);
    }

    @Test
    public void getInvalidActivationCode_returnActivationCodeNotFoundException() {
        when(userRepo.findByActivationCode("NotActivationCode")).thenReturn(null);
        assertThrows(ActivationCodeNotFoundException.class, () -> registrationService.activation("NotActivationCode"));
    }

    @Test
    public void getValidActivationCode() {
        when(userRepo.findByActivationCode("activationCode")).thenReturn(user);
        registrationService.activation("activationCode");
        verify(userRepo, times(1)).save(user);
    }
}
