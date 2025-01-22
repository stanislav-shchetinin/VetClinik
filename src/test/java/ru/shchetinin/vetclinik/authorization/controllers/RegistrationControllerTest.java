package ru.shchetinin.vetclinik.authorization.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shchetinin.vetclinik.VetClinikApplication;
import ru.shchetinin.vetclinik.repositories.UserRepository;
import ru.shchetinin.vetclinik.entities.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VetClinikApplication.class
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
        Optional<User> user = userRepo.findByUsername("stas.shc@gmail.com");
        user.ifPresent(value -> userRepo.delete(value));
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

        Optional<User> user = userRepo.findByUsername(email);
        assertThat(user).isPresent();

        mockMvc.perform(get(URI_ACTIVATION + "/" + user.get().getActivationCode()))
                .andExpect(status().isOk());

    }

    @Test
    public void getInvalidActivationCode_returnActivationCodeNotFoundException() throws Exception {
         mockMvc.perform(get(URI_ACTIVATION + "/1"))
                 .andExpect(status().isNotFound());
    }


}
