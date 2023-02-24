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

    @JsonBackReference
    @ManyToOne()
    private CollectionT collection;
    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private Collection<Product> products;

    public Category(String title) {
        this.title = title;


    }
}
