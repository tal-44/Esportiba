package tfg.backend.domain.port;

import tfg.backend.domain.model.Product;

public interface IProductRepository {
    Product save(Product product);
    Iterable<Product> findAll();
    Product findById(Integer id);
    void deleteById(Integer id);
    Product updateById(Product product);
}
