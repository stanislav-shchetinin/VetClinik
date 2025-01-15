package ru.shchetinin.groupmanager.authorization.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import ru.shchetinin.groupmanager.authorization.utils.JwtTokenUtils;

import io.jsonwebtoken.SignatureException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtTokenUtilsTest {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    private final User user = new User("admin@admin.ru",
            "12345",
            Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
    @Test
    public void getCorrectToken_returnCorrectUsername() {
        String token = jwtTokenUtils.generateToken(user);
        assertEquals(jwtTokenUtils.getUsername(token), user.getUsername());
    }

    @Test
    public void getCorrectToken_returnCorrectRoles() {
        String token = jwtTokenUtils.generateToken(user);
        assertEquals(jwtTokenUtils.getRoles(token), user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
    }

    @Test
    public void getInvalidToken_returnSignatureException() {
        String token = jwtTokenUtils.generateToken(user) + "1";
        assertThrows(SignatureException.class, () -> jwtTokenUtils.getUsername(token));
    }

    @Test
    public void getCorrectUser_returnCorrectToken() {
        String token = jwtTokenUtils.generateToken(user);
        assertThat(token)
                .isNotNull()
                .isNotEmpty()
                .isNotBlank()
                .containsPattern(".*\\..*\\.*");
    }

}
