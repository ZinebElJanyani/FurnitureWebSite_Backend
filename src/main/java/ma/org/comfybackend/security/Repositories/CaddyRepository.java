package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Caddy;
import ma.org.comfybackend.security.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaddyRepository extends JpaRepository<Caddy,Integer> {

   // Caddy findByCustomer_Id(int id);
    Caddy findByCustomer(Customer c);

}
