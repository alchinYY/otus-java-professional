package ru.otus.professional.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}