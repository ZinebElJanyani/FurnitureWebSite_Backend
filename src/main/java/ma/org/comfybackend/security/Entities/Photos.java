package ma.org.comfybackend.security.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor

public class Photos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String imagePath;

    @ManyToOne()
    @JsonBackReference
    private Product product;

    public Photos(String imagePath) {
        this.imagePath = imagePath;

    }
}
