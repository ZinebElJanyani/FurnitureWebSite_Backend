package ma.org.comfybackend.security.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ma.org.comfybackend.security.Entities.CollectionT;

import javax.persistence.ManyToOne;

public class CategoryDTO {
    private int id;
    private String title;


    private String collection;


    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
