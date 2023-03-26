package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardPaymentRepository extends JpaRepository<CardPayment, Integer> {
}