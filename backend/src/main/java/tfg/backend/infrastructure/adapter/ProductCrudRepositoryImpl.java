package tfg.backend.infrastructure.adapter;

import tfg.backend.domain.model.Product;
import tfg.backend.domain.port.IProductRepository;
import tfg.backend.infrastructure.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductCrudRepositoryImpl implements IProductRepository {
    private final IProductCrudRepository iProductCrudRepository;
    private final ProductMapper productMapper;

    /*

        Otra opcion aparte de @AllArgsConstructor es hacer el constructor tu mismo

    public ProductCrudRepositoryImpl(IProductCrudRepository iProductCrudRepository, ProductMapper productMapper) {
        this.iProductCrudRepository = iProductCrudRepository;
        this.productMapper = productMapper;
    }
     */

    @Override
    public Product save(Product product) {
        return productMapper.toProduct(iProductCrudRepository.save(productMapper.toProductEntity(product)));
    }

    @Override
    public Iterable<Product> findAll() {
        return productMapper.toProductList(iProductCrudRepository.findAll());
    }

    @Override
    public Product findById(Integer id) {
        return productMapper.toProduct(iProductCrudRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto con id: " + id + " no se encuentra")
        ));
    }

    @Override
    public void deleteById(Integer id) {
        iProductCrudRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto con id: " + id + " no se encuentra")
        );

        iProductCrudRepository.deleteById(id);
    }

    @Override
    public Product updateById(Product product) {
        return null;
    }
}