package ma.org.comfybackend.security.DTO;

import ma.org.comfybackend.security.Enumerations.Color;
import ma.org.comfybackend.security.Enumerations.Material;

import java.util.Date;

public class ProductDTO {
    private int id;
    private String nom;
    private String description;
    private double price;
    private int qteStock;
    private int promotion;
    private Date created_at;
    private boolean deleted;
    private Color color;
    private Material material;
    private boolean selected;

    private double stars;

    public void setStars(double stars) {
        this.stars = stars;
    }

    public double getStars() {
        return stars;
    }

    public ProductDTO() {

    }

    public ProductDTO(int id, String nom, String description, double price, int qteStock, int promotion, Date createdAt, boolean style, Color color, Material material, boolean selected) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.price = price;
        this.qteStock = qteStock;
        this.promotion = promotion;
        created_at = createdAt;
        this.deleted = style;
        this.color = color;
        this.material = material;
        this.selected = selected;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQteStock() {
        return qteStock;
    }

    public int getPromotion() {
        return promotion;
    }

    public Date getCreated_at() {
        return created_at;
    }



    public Color getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }



    public void setColor(Color color) {
        this.color = color;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
