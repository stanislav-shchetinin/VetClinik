package ru.shchetinin.vetclinik.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "groups_e")
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Comparable<Request>{

    @Id
    private UUID id;

    private String phone;
    private String bread;
    private String size;
    private String weight;
    private String specialRequirements;
    private String street;
    private String house;
    private String flat;
    private Long clinicId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_name", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User owner;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<JoinedUserRequest> jug = new ArrayList<>();

    public Request(UUID id, String phone, String bread, String size, String weight, String specialRequirements, String street, String house, String flat, Long clinicId, User owner) {
        this.id = id;
        this.phone = phone;
        this.bread = bread;
        this.size = size;
        this.weight = weight;
        this.specialRequirements = specialRequirements;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.clinicId = clinicId;
        this.owner = owner;
    }

    @Override
    public int compareTo(Request o) {
        return id.compareTo(o.id);
    }
}
