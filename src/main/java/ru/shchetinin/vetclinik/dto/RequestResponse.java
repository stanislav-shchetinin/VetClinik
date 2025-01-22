package ru.shchetinin.vetclinik.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponse implements Serializable {
    private Long id; // Уникальный ID
    private String status; // Статус запроса
    private String phone;
    private String breed;
    private String size;
    private String weight;
    private String specialRequirements; // Особые требования
    private String street; // Улица клиента
    private String house; // Дом клиента
    private String flat; // Квартира клиента
    private String clinicPhone; // Телефон клиники
    private String clinicName; // Название клиники
    private String openHours; // Часы работы клиники
    private String clinicStreet; // Улица клиники
    private String clinicHouse; // Дом клиники
    private String clinicFlat; // Квартира клиники
}
