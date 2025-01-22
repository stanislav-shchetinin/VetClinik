package ru.shchetinin.vetclinik.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String flat;

    private String openHours;

    private String clinicPhone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_user_id", referencedColumnName = "username")
    @JsonIgnore
    private User adminUser;

}

