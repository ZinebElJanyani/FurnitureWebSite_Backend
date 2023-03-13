package ma.org.comfybackend.security.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Customer extends AppUser{
    private String birthday;

    @OneToOne
    private Caddy caddy;

}
