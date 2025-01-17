package ru.shchetinin.vetclinik.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.shchetinin.vetclinik.authorization.roles.RoleAdd;

import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Comparable<User>{

    @Id
    private String username;
    private String password;
    private String activationCode;
    private boolean enabled;
    private RoleAdd role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JoinedUserRequest> jug = new ArrayList<>();

    public User(String username, String password, String activationCode, boolean enabled, RoleAdd roleAdd) {
        this.username = username;
        this.password = password;
        this.activationCode = activationCode;
        this.enabled = enabled;
        this.role = roleAdd;
    }

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.getUsername());
    }
}
