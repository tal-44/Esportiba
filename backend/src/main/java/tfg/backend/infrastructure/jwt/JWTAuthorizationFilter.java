package tfg.backend.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tfg.backend.infrastructure.Service.CustomUserDetailService;

import java.io.IOException;

import static tfg.backend.infrastructure.jwt.JWTValidate.*;

@Component
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;

    public JWTAuthorizationFilter(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            if (tokenExists(request, response)) {

                Claims claims = JWTValid(request);

                if (claims.get("authorities") != null) {
                    setAuthentication(claims, customUserDetailService);
                } else {
                    log.error("JWT token does not contain authorities");
                    SecurityContextHolder.clearContext();
                }

            } else {
                log.error("JWT token is missing or invalid");
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

    }
}
