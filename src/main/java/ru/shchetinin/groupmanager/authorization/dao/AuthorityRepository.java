package ru.shchetinin.groupmanager.authorization.dao;

import org.springframework.data.repository.CrudRepository;
import ru.shchetinin.groupmanager.authorization.entities.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, String> {
    Authority findByUsername(String username);
}