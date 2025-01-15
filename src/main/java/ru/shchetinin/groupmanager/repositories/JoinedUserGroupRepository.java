package ru.shchetinin.groupmanager.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shchetinin.groupmanager.entities.Group;
import ru.shchetinin.groupmanager.entities.JoinedUserGroup;
import ru.shchetinin.groupmanager.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinedUserGroupRepository extends CrudRepository<JoinedUserGroup, Long> {

    JoinedUserGroup findByUserAndGroup(User user, Group group);

}
