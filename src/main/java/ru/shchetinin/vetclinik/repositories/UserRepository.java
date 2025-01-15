package ru.shchetinin.vetclinik.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.shchetinin.vetclinik.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
    User findByActivationCode(String activationCode);

}