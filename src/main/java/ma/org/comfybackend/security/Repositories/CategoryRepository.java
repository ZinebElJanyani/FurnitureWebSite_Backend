package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
