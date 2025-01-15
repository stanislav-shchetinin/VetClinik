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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VetClinikApplication.class
)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String URI = "/auth";
    @Test
    public void getCorrectAuthRequest_returnOkToken() throws Exception {
        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"admin@admin.ru\", \"password\": \"12345\"}")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getInvalidPassword_returnNotFound() throws Exception {
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin@admin.ru\", \"password\": \"1234\"}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void getInvalidEmail_returnNotFound() throws Exception {
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"admin@admin.r\", \"password\": \"12345\"}")
                )
                .andExpect(status().isNotFound());
    }


}
