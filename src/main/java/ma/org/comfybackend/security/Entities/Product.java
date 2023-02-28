package ma.org.comfybackend.security.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.org.comfybackend.security.Enumerations.Color;
import ma.org.comfybackend.security.Enumerations.Material;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor

public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private double price;
    private int qteStock;
    private int promotion;
    private Date created_at;
    private String style;
    private Color color;
    private Material material;
    private boolean selected;
    @ManyToOne()

    private Category category;

    @OneToMany(mappedBy = "product")

    private Collection<Photos> images;

    public Product(String nom, String description, double price, int qteStock, int promotion, Date created_at, String style, Color color, Material material, boolean selected, Category category, Collection<Photos> images) {
        this.nom = nom;
        this.description = description;
        this.price = price;
        this.qteStock = qteStock;
        this.promotion = promotion;
        this.created_at = created_at;
        this.style = style;
        this.color = color;
        this.material = material;
        this.selected = selected;
        this.category = category;
        this.images = images;
    }
}
