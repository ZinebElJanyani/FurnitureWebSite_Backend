package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Item;
import ma.org.comfybackend.security.Entities.Photos;
import ma.org.comfybackend.security.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {

    Item findByProduct(Product product);
}
