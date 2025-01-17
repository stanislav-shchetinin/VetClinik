package ru.shchetinin.vetclinik.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shchetinin.vetclinik.entities.Request;
import ru.shchetinin.vetclinik.entities.JoinedUserRequest;
import ru.shchetinin.vetclinik.entities.User;

@Repository
public interface JoinedUserGroupRepository extends CrudRepository<JoinedUserRequest, Long> {

    JoinedUserRequest findByUserAndGroup(User user, Request group);

}
