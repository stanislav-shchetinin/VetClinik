package ru.shchetinin.vetclinik.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_groups")
@Getter
@Setter
public class JoinedUserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    private Integer numberClasses;
}
