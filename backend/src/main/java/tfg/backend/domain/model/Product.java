package tfg.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String code;
    private String description;
    private BigDecimal price;
    private String urlImage;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Integer userId;
    private Integer categoryId;
}
