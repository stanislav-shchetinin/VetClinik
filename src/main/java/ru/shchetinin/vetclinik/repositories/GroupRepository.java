package ru.shchetinin.vetclinik.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shchetinin.vetclinik.entities.Group;

import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<Group, UUID> {
}
