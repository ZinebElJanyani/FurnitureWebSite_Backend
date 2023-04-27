package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByEmail(String name);
    AppUser findByName(String name);

    @Query(value = "SELECT u.email FROM AppUser u ")
    List<String> findAllEmail();
}
