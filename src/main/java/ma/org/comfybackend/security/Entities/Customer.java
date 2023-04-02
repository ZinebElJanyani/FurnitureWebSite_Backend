package ma.org.comfybackend.security.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer extends AppUser{
    private String birthday;

    @ManyToMany
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Collection<Product> products;

    @JsonBackReference
    @OneToOne
    private Caddy caddy;

    @JsonBackReference
    @OneToMany(mappedBy = "customer")
    Collection<Command> commands;

    @JsonBackReference
    @OneToMany(mappedBy = "customer")
    Collection<DeliveryAdress> deliveryAdress;

    @JsonBackReference
    @OneToMany(mappedBy = "customer")
    Collection<Review> reviews;

}
