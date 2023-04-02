package ma.org.comfybackend.security.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int nbre_etoile;
    String titre;
    String text;
    Date creeA;
    String image;

    boolean isRecommanded;
    String name;


    @ManyToOne()
    Product product;

    @ManyToOne()
    Customer customer;



}
