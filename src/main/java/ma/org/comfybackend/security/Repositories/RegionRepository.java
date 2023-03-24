package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region,Integer> {

}
