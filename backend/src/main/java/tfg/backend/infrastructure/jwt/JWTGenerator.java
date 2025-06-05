package tfg.backend.infrastructure.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static tfg.backend.infrastructure.jwt.Constants.SUPER_SECRET_KEY;
import static tfg.backend.infrastructure.jwt.Constants.getSignedKey;

@Service
public class JWTGenerator {

    public String getToken(String username) {

        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().toString()
        );

        String token = Jwts.builder()
                .setId("esportibaEcommerce")

                .setSubject(username)
                .claim("authorities", authorityList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration( new Date (System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
                .signWith(getSignedKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512).compact();


        return "Bearer " + token;
    }

}
