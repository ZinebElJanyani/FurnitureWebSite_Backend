package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.City;
import ma.org.comfybackend.security.Entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Integer> {
}
