package ru.shchetinin.groupmanager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shchetinin.groupmanager.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
    User findByActivationCode(String activationCode);

}