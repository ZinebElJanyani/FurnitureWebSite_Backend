package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Caddy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaddyRepository extends JpaRepository<Caddy,Long> {


}
