package tfg.backend.infrastructure.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tfg.backend.application.services.ProductService;
import tfg.backend.domain.model.Product;
import tfg.backend.domain.model.User;
import tfg.backend.domain.port.IUserRepository;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/admin/products")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8085"})
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final IUserRepository iUserRepository;

    @PostMapping
    public ResponseEntity<Product> save(
            @RequestParam("id") String id,
            @RequestParam("code") String code,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("urlImage") String urlImage,
    //        @RequestParam("userId") Integer userId,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam(value = "image", required = false)MultipartFile multipartFile
    ) throws IOException {

        Product product = new Product();

        if(!id.equals("0") && !id.isEmpty()){
            product.setId(Integer.parseInt(id));
        }

        product.setCode(code);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setUrlImage(urlImage);
    //    product.setUserId(userId);
        product.setCategoryId(categoryId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = iUserRepository.findByEmail(email); // ← necesitas inyectar esto

        product.setUserId(user.getId());


        log.info("Nombre producto: {}", product.getName());
        return new ResponseEntity<>(productService.save(product, multipartFile), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
