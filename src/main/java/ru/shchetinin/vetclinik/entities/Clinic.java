package ru.shchetinin.vetclinik.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clinic")
@AllArgsConstructor
@NoArgsConstructor
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String street;
    private String house;
    private String openHours;
    private String clinicPhone;

    @OneToOne
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;
}
