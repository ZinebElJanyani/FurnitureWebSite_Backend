package ma.org.comfybackend.security.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float totalPrice;
    private float deliveryPrice;

    @OneToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "caddy")
    Collection<Item> items;

}
