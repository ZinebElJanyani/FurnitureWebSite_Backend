package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String name);
    Customer findByName(String name);

    @Query("SELECT COUNT(c) FROM Customer c WHERE MONTH(c.created_at) = MONTH(CURRENT_DATE()) AND YEAR(c.created_at) = YEAR(CURRENT_DATE())")
    Long countCustomersInCurrentMonth();


}
