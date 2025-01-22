package ru.shchetinin.vetclinik.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "request")
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String breed;

    private String size;

    private String weight;

    private String specialRequirements;

    private String street;

    private String house;

    private String flat;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    @JsonIgnore
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "carrier_id")
    @JsonIgnore
    private Carrier carrier;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

}
