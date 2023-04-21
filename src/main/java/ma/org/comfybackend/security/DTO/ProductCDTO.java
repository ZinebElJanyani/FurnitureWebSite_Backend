package ma.org.comfybackend.security.DTO;

import ma.org.comfybackend.security.Enumerations.Color;
import ma.org.comfybackend.security.Enumerations.Material;

import java.util.Date;

public class ProductCDTO {
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

    private int categoryId;
    private String categorytitle;

    public void setCategorytitle(String categorytitle) {
        this.categorytitle = categorytitle;
    }

    public String getCategorytitle() {
        return categorytitle;
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

    public int getCategoryId() {
        return categoryId;
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

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
