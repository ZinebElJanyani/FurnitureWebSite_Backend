package ma.org.comfybackend.security.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
//@Data génére les getters et les setters avec lombok
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Category implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String collection;
    private String imageC;
    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private Collection<Product> products;

    public Category(String title, String collection, String imageC) {
        this.title = title;
        this.collection = collection;
        this.imageC = imageC;

    }
}
