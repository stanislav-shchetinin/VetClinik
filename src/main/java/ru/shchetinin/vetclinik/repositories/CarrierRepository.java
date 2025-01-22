package ru.shchetinin.vetclinik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shchetinin.vetclinik.entities.Carrier;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {
}
