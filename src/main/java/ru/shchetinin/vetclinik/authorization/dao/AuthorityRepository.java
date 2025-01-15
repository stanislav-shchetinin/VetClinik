package ru.shchetinin.vetclinik.authorization.dao;

import org.springframework.data.repository.CrudRepository;
import ru.shchetinin.vetclinik.authorization.entities.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, String> {
    Authority findByUsername(String username);
}