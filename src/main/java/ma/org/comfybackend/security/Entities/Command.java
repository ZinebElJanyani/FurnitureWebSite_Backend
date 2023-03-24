package ma.org.comfybackend.security.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.org.comfybackend.security.Enumerations.CommandState;
import ma.org.comfybackend.security.Enumerations.PaymentMethod;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Command implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String ref;
    Date date;

    PaymentMethod paymentMethod;

    CommandState commandState;

    Date deliveryDate;

    boolean withAssembly;
    float assemblyPrice;

    float totalPrice;
    float deliveryPrice;
     float couponDiscount;

     @ManyToOne()
     Customer customer;

     @OneToOne()
     CardPayment cardPayment;

     @ManyToOne()
     DeliveryAdress deliveryAdress;

    @OneToMany(mappedBy = "command")
    Collection<CommandItem> commandItems;

}
