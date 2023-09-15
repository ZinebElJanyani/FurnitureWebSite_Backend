package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Customer;
import ma.org.comfybackend.security.Entities.DeliveryAdress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAdressRepository extends JpaRepository<DeliveryAdress, Integer> {

  List<DeliveryAdress> findByIsSavedIsTrueAndCustomer(Customer c);
}