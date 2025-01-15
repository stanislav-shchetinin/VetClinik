package ru.shchetinin.groupmanager.authorization.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "authorities")
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    private String username;
    private String authority;
}