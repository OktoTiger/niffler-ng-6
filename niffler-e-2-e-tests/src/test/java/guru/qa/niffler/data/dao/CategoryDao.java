package guru.qa.niffler.data.dao;

import guru.qa.niffler.data.entity.spend.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryDao {
    CategoryEntity create(CategoryEntity spend);

    Optional<CategoryEntity> findCategoryById(UUID id);

    void deleteCategory(CategoryEntity category);

    List<CategoryEntity> findAllByUsername(String username);

    Optional<CategoryEntity> findCategoryByUsernameAndCategoryName(String username, String categoryName);
}
