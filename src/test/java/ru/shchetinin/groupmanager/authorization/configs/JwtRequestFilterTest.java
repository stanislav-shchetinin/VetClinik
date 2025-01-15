package ru.shchetinin.groupmanager.authorization.configs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shchetinin.groupmanager.GroupManagerApplication;
import ru.shchetinin.groupmanager.authorization.utils.JwtTokenUtils;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = GroupManagerApplication.class
)
@AutoConfigureMockMvc
public class JwtRequestFilterTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Test
    public void getCorrectRegRequest_returnOk() throws Exception {
        User user = new User("admin@admin.ru",
                "12345",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        String token = jwtTokenUtils.generateToken(user);
        mockMvc.perform(get("/groups")
                .header("Authorization", String.format("Bearer %s", token)))
                .andExpect(status().isOk());
    }

    @Test
    public void getWithoutHeader_returnUnauthorized() throws Exception {
        mockMvc.perform(get("/groups"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getInvalidToken_returnUnauthorized() throws Exception {
        User user = new User("admin@admin.ru",
                "12345",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        String token = jwtTokenUtils.generateToken(user);
        mockMvc.perform(get("/groups")
                        .header("Authorization", String.format("Bearer %s1", token)))
                .andExpect(status().isUnauthorized());
    }

}
