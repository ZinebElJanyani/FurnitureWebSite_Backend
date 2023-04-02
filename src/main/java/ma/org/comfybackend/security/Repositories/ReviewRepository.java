package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Entities.Region;
import ma.org.comfybackend.security.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findByProduct(Product p);

}
