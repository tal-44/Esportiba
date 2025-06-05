package tfg.backend.infrastructure.config;

import tfg.backend.application.services.*;
import tfg.backend.domain.port.IUserRepository;
import tfg.backend.domain.port.ICategoryRepository;
import tfg.backend.domain.port.IProductRepository;
import tfg.backend.domain.port.IOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserService userService(IUserRepository iUserRepository) {
        return new UserService(iUserRepository);
    }

    @Bean
    public CategoryService categoryService(ICategoryRepository iCategoryRepository){
        return new CategoryService(iCategoryRepository);
    }

    @Bean
    public ProductService productService(IProductRepository iProductRepository, UploadFile uploadFile){
        return new ProductService(iProductRepository, uploadFile);
    }

    @Bean
    public OrderService orderService(IOrderRepository iOrderRepository){
        return new OrderService(iOrderRepository);
    }

    @Bean
    public UploadFile uploadFile(){
        return new UploadFile();
    }
/*
    Con la anotacion:
            @CrossOrigin(origins = "http://localhost:4200")
    en la clase ProductController.java era suficiente para permitir el acceso desde esta URL.

    ESte metodo tambien permite restringir metodos, cabeceras, credenciales...

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Permite CORS en todas las rutas que comiencen con /api
                        .allowedOrigins("http://localhost:4200") // Permite solicitudes desde localhost:4200
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Cabeceras permitidas
                        .allowCredentials(true); // Permite credenciales (cookies, autenticación)
            }
        };
    }
*/

    @Bean
    public RegistrationService registrationService(IUserRepository iUserRepository){
        return new RegistrationService(iUserRepository);
    }

}
