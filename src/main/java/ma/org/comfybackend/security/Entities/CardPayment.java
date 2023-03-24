package ma.org.comfybackend.security.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardPayment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    int cardNumber;

    int CVC;

    @Temporal(TemporalType.DATE)
    Date expDate;

    @OneToOne(mappedBy = "cardPayment")
    Command command;
}
