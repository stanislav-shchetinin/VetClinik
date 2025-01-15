package ru.shchetinin.vetclinik.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shchetinin.vetclinik.entities.Group;
import ru.shchetinin.vetclinik.entities.JoinedUserGroup;
import ru.shchetinin.vetclinik.entities.User;

@Repository
public interface JoinedUserGroupRepository extends CrudRepository<JoinedUserGroup, Long> {

    JoinedUserGroup findByUserAndGroup(User user, Group group);

}
