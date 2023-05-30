package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Override
    List<Product> findAll();

    List<Product>findBySelectedIsTrueAndPriceBetween(double min,double max);

    List<Product>findByCategoryIdAndPriceBetween(int id,double min,double max);

    List<Product> findByNomContains(String val);

    List<Product> findByDeletedIsFalse();

    List<Product> findByQteStockEquals(int val);

    @Query("SELECT COUNT(p) FROM Product p WHERE MONTH(p.created_at) = MONTH(CURRENT_DATE()) AND YEAR(p.created_at) = YEAR(CURRENT_DATE())")
    Long countProductsInCurrentMonth();



}
