package ma.org.comfybackend.security.DTO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CaddyDTO {


    private float totalPrice;
    private float deliveryPrice;
    private float coupon;

    public CaddyDTO( float totalPrice, float deliveryPrice, float coupon) {

        this.totalPrice = totalPrice;
        this.deliveryPrice = deliveryPrice;
        this.coupon = coupon;
    }



    public float getTotalPrice() {
        return totalPrice;
    }

    public float getDeliveryPrice() {
        return deliveryPrice;
    }

    public float getCoupon() {
        return coupon;
    }



    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public void setCoupon(float coupon) {
        this.coupon = coupon;
    }
}
