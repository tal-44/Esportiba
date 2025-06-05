package tfg.backend.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import tfg.backend.infrastructure.Service.CustomUserDetailService;

import static tfg.backend.infrastructure.jwt.Constants.*;

public class JWTValidate {

    //comprueba si el token existe en la petición
    public static boolean tokenExists(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX))
            return false;
        return true;

    }

    //comprueba si el token es válido
    public static Claims JWTValid(HttpServletRequest request) {

        String jwtToken = request.getHeader(HEADER_AUTHORIZATION).replace(TOKEN_BEARER_PREFIX, "");

        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey(SUPER_SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken).getBody();

    }

    // autentica al usuario
    public static void setAuthentication(Claims claims, CustomUserDetailService customUserDetailService) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.getSubject());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
