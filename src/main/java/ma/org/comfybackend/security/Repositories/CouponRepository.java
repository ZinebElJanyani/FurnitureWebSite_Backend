package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon,Integer> {
    Coupon findByCode(String code);
}
