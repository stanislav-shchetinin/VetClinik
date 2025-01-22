package ru.shchetinin.vetclinik.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shchetinin.vetclinik.entities.Clinic;

import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    @Query("SELECT c FROM Clinic c WHERE c.adminUser.username = :username")
    Clinic findByAdminUsername(String username);
    @Query(value = "SELECT c FROM Clinic c ORDER BY c.id")
    Page<Clinic> findAllClinics(Pageable pageable);
}
