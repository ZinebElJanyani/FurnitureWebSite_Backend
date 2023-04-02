package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Command;
import ma.org.comfybackend.security.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Integer> {
    List<Command> findByCustomer(Customer c);
}