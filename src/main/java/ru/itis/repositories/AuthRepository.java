package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.Auth;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    void deleteByUserEmailAndCookieValue(String email, String value);

    Optional<Auth> findByCookieValue(String value);

    Optional<Auth> findByCookieValueAndUserEmail(String value, String userEmail);
}
