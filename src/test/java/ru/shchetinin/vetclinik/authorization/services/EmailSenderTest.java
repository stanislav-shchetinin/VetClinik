package ru.shchetinin.vetclinik.authorization.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailSenderTest {

    @InjectMocks
    @Autowired
    private EmailSender emailSenderService;

    @MockBean
    private JavaMailSender emailSender;

    @Test
    public void getCorrectMail_ok() {
        emailSenderService.send("stas.shc@gmail.com", "TestMail",
                "Nothing personal, just tests");
        verify(emailSender, times(1)).send((SimpleMailMessage) any());
    }

}
