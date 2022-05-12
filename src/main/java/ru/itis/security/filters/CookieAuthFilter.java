package ru.itis.security.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.models.Auth;
import ru.itis.repositories.AuthRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieAuthFilter extends OncePerRequestFilter {

    private final AuthRepository authRepository;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains("/signIn") && request.getCookies() != null) {

            Optional<Cookie> authorizationOpt = Arrays
                    .stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("Authorization"))
                    .findFirst();

            if (authorizationOpt.isPresent()) {

                Optional<Auth> authOptional = authRepository
                        .findByCookieValue(authorizationOpt.get().getValue());

                if (authOptional.isPresent()) {
                    UserDetails details = userDetailsService.loadUserByUsername(authOptional.get().getUserEmail());

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details.getUsername(),
                            authOptional.get().getCookieValue(),
                            details.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
