package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    User getById(Long aLong);

    @Override
    List<User> findAll();

    @Override
    <S extends User> S saveAndFlush(S entity);

    @Override
    <S extends User> S save(S entity);

    @Override
    void deleteById(Long aLong);

    User findByUsername(String username);

}
