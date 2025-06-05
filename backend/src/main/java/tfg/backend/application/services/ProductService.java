package tfg.backend.application.services;

import tfg.backend.domain.model.Product;
import tfg.backend.domain.port.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public class ProductService {
    private final IProductRepository iProductRepository;
    private final UploadFile uploadFile;

    public ProductService(IProductRepository iProductRepository, UploadFile uploadFile) {
        this.iProductRepository = iProductRepository;
        this.uploadFile = uploadFile;
    }

    public Product save(Product product, MultipartFile multipartFile) throws IOException {

        log.info("Product ID: {}", product.getId());

        if (product.getId()!=null){  // cuando es un producto modificado
            if (multipartFile==null){
                Product productO = iProductRepository.findById(product.getId());
                product.setUrlImage(productO.getUrlImage());
            } else {

                if (product.getUrlImage()!=null && product.getUrlImage().length()>1) {

                    String nameFile = product.getUrlImage().substring(29);
                    log.info("1Nombre imagen: {}", nameFile);

                    if (!nameFile.equals("default.jpg")) {
                        uploadFile.delete(nameFile);
                    }

                } else {
                    product.setUrlImage(uploadFile.upload(multipartFile));

                }

                product.setUrlImage(uploadFile.upload(multipartFile));
            }

        } else {
            product.setUrlImage(uploadFile.upload(multipartFile));
        }

        return this.iProductRepository.save(product);
    }

    public Iterable<Product> findAll() {
        return iProductRepository.findAll();
    }
    public Product findById(Integer id) {
        return iProductRepository.findById(id);
    }
    public Product updateById(Product product) {
        return iProductRepository.updateById(product);
    }
    public void deleteById(Integer id) {
        Product product = findById(id);

        if (product.getUrlImage()!=null  && product.getUrlImage().length()>1) {

            String nameFile = product.getUrlImage().substring(29);
            log.info("2Nombre imagen: {}", nameFile);

            if (!nameFile.equals("default.jpg")) {
                uploadFile.delete(nameFile);
            }

        }

        iProductRepository.deleteById(id);
    }
}
