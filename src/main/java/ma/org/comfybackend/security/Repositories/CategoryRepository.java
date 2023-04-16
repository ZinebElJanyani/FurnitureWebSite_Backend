package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.CollectionT;
import ma.org.comfybackend.security.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "select c.collection From Category c where c.id = :id")
    CollectionT getCollectionByCategoryId(int id);

    Category findById(int id);
}
