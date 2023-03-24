package ma.org.comfybackend.security.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAdress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String addess;

    @ManyToOne()
    City city;

    @OneToMany(mappedBy = "deliveryAdress")
    Collection<Command> commands;
}
