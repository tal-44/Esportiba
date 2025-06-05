package tfg.backend.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfg.backend.application.services.ProductService;
import tfg.backend.domain.model.Product;

@RestController
@RequestMapping("/api/v1/home")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085"})
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }

}
