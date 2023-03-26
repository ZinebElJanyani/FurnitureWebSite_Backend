package ma.org.comfybackend.security.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer extends AppUser{
    private String birthday;

    @JsonBackReference
    @OneToOne
    private Caddy caddy;

    @JsonBackReference
    @OneToMany(mappedBy = "customer")
    Collection<Command> commands;

    @JsonBackReference
    @OneToMany(mappedBy = "customer")
    Collection<DeliveryAdress> deliveryAdress;

}
