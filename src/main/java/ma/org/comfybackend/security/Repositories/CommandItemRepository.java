package ma.org.comfybackend.security.Repositories;


import ma.org.comfybackend.security.Entities.CommandItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandItemRepository extends JpaRepository<CommandItem,Integer> {

}
