package ma.org.comfybackend.security.DTO;

import java.sql.Date;

public class ReviewDTO {

    int id;
    int nbre_etoile;
    String titre;
    String text;
    Date creeA;
    String image;

    String name;
    boolean isRecommanded;

    int id_customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbre_etoile() {
        return nbre_etoile;
    }

    public String getTitre() {
        return titre;
    }

    public String getText() {
        return text;
    }

    public Date getCreeA() {
        return creeA;
    }

    public String getImage() {
        return image;
    }

    public boolean getIsRecommanded() {
        return isRecommanded;
    }



    public void setNbre_etoile(int nbre_etoile) {
        this.nbre_etoile = nbre_etoile;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreeA(Date creeA) {
        this.creeA = creeA;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRecommanded(boolean recommanded) {
        isRecommanded = recommanded;
    }
}
