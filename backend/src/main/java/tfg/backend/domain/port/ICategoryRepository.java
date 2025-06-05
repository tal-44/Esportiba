package tfg.backend.domain.port;

import tfg.backend.domain.model.Category;

public interface ICategoryRepository {
    Category save(Category category);
    Iterable<Category> findAll();
    Category findById(Integer id);
    void deleteById(Integer id);
    Category updateById(Category category);
}
