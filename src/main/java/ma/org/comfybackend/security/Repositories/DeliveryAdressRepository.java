package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.DeliveryAdress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAdressRepository extends JpaRepository<DeliveryAdress, Integer> {
}