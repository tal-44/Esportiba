package tfg.backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import tfg.backend.infrastructure.jwt.JWTAuthorizationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
                    corsConfiguration.setAllowedMethods(Arrays.asList("*"));
                    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
                    return corsConfiguration;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Permitir rutas de la SPA y recursos estáticos
                        .requestMatchers("/", "/index.html").permitAll()
                        .requestMatchers(
                                "/**/*.css",
                                "/**/*.html",
                                "/**/*.js",
                                "/**/*.png",
                                "/**/*.jpg",
                                "/**/*.jpeg",
                                "/**/*.gif",
                                "/**/*.webp",
                                "/**/*.svg",
                                "/assets/**",
                                "/favicon.ico"
                        ).permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/images/*.webp").permitAll()

                        // Permitir páginas públicas de la API
                        .requestMatchers("/api/v1/home/**").permitAll()
                        .requestMatchers("/api/v1/payments/success").permitAll()
                        .requestMatchers("/api/v1/security/**").permitAll()

                        // Rutas protegidas por rol
                        .requestMatchers("/api/v1/admin/categories/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/admin/products/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/orders/**").hasRole("USER")
                        .requestMatchers("/api/v1/payments/**").hasRole("USER")

                        // Cualquier otra petición a /api/** requiere autenticación
                        .requestMatchers("/api/**").authenticated()

                        // Cualquier otra ruta (SPA) se permite para que el FrontendController la maneje
                        .anyRequest().permitAll()
                )
                .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
