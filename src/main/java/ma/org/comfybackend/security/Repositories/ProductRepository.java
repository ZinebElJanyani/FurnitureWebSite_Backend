package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Override
    List<Product> findAll();

    List<Product>findBySelectedIsTrue();

    List<Product>findByCategoryId(int id);
}
