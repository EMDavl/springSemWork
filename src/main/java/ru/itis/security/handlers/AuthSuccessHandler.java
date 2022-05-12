package ru.itis.security.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itis.models.Auth;
import ru.itis.repositories.AuthRepository;
import ru.itis.security.details.UserDetailsImpl;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final int COOKIE_MAX_AGE = 24 * 60 * 60;
    private static final String COOKIE_NAME = "Authorization";

    private final AuthRepository authRepository;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Cookie authCookie = new Cookie(COOKIE_NAME, UUID.randomUUID().toString());
        authCookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(authCookie);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Auth auth = Auth.builder()
                .cookieValue(authCookie.getValue())
                .userEmail(userDetails.getEmail())
                .build();

        authRepository.save(auth);
        redirectStrategy.sendRedirect(request, response, "/feed");
    }
}
