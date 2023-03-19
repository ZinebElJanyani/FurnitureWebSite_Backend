package ma.org.comfybackend.security.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caddy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float totalPrice;
    private float deliveryPrice;
    private float coupon;

    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    Customer customer;


    @OneToMany(mappedBy = "caddy")
    Collection<Item> items;

}
