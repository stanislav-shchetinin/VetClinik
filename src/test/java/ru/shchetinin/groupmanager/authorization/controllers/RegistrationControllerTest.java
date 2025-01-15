package ru.shchetinin.groupmanager.authorization.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shchetinin.groupmanager.GroupManagerApplication;
import ru.shchetinin.groupmanager.repositories.UserRepository;
import ru.shchetinin.groupmanager.entities.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = GroupManagerApplication.class
)
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepo;

    private final String URI_REG = "/registration";
    private final String URI_ACTIVATION = "/activation";

    @Test
    public void getCorrectRegRequest_returnOk() throws Exception {
        User user = userRepo.findByUsername("stas.shc@gmail.com");
        if (user != null)
            userRepo.delete(user);
        mockMvc.perform(post(URI_REG)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"stas.shc@gmail.com\", \"password\": \"12345\"}")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void getAlreadyExistsUser_returnConflict() throws Exception {
        mockMvc.perform(post(URI_REG)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin@admin.ru\", \"password\": \"12345\"}")
                )
                .andExpect(status().isConflict());
    }

    @Test
    public void getValidActivationCode_returnOk() throws Exception {
        String email = "stas.shc@gmail.com";
        getCorrectRegRequest_returnOk();

        User user = userRepo.findByUsername(email);
        assertThat(user).isNotNull();

        mockMvc.perform(get(URI_ACTIVATION + "/" + user.getActivationCode()))
                .andExpect(status().isOk());

    }

    @Test
    public void getInvalidActivationCode_returnActivationCodeNotFoundException() throws Exception {
         mockMvc.perform(get(URI_ACTIVATION + "/1"))
                 .andExpect(status().isNotFound());
    }


}
