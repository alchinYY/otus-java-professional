package ru.otus.professional.dao;

import ru.otus.professional.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findByLogin(String login);
}
