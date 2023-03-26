package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Integer> {
}