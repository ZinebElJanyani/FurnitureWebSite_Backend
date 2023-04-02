package ma.org.comfybackend.security.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

public class CustomerRegisterDTO {

    private int id;

    private String name;

    private String password;
    private String email;

    private String phone;

    private Date created_at;

    private Date updated_at;

    private String role;
    private String birthday;

    public CustomerRegisterDTO(String name, String password, String email, String phone, Date created_at, Date updated_at, String role, String birthday) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.role = role;
        this.birthday = birthday;
    }

    public CustomerRegisterDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public String getRole() {
        return role;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
