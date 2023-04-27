package ma.org.comfybackend.security.Repositories;


import ma.org.comfybackend.security.Entities.CommandItem;
import ma.org.comfybackend.security.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommandItemRepository extends JpaRepository<CommandItem,Integer> {
    @Query("SELECT COUNT(c) FROM CommandItem c WHERE c.product = :givenProduct")
    Long countCommandsForProduct(@Param("givenProduct")Product product);
}
