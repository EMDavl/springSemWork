package ru.itis.security.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import ru.itis.models.Auth;
import ru.itis.repositories.AuthRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final AuthRepository repository;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null && request.getCookies() != null) {
            Optional<Cookie> authorizationOpt = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("Authorization"))
                    .findFirst();

            if (authorizationOpt.isPresent()) {
                Cookie authorization = authorizationOpt.get();
                String value = authorization.getValue();
                Optional<Auth> auth = repository.findByCookieValueAndUserEmail(value, (String) authentication.getPrincipal());

                auth.ifPresent(repository::delete);

                authorization.setMaxAge(1);
                response.addCookie(authorization);
            }
        }
    }
}
