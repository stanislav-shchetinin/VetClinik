package ru.shchetinin.vetclinik.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shchetinin.vetclinik.entities.Request;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE r.user.id = :userId")
    Page<Request> findRequestsByUserId(@Param("userId") String userId, Pageable pageable);
    @Query("SELECT r FROM Request r WHERE r.clinic.id = :clinicId OR :clinicId IS NULL")
    Page<Request> findRequestsByClinicId(Long clinicId, Pageable pageable);
    boolean existsById(Long id);
}

