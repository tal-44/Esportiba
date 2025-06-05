package tfg.backend.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cualquier petición a /images/** se sirve desde classpath:/static/images/
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
