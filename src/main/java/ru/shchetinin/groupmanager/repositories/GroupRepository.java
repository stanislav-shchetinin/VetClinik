package ru.shchetinin.groupmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shchetinin.groupmanager.entities.Group;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends CrudRepository<Group, UUID> {
}
