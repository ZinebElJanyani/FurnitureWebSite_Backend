package ma.org.comfybackend;

import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.Photos;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Enumerations.Color;
import ma.org.comfybackend.security.Enumerations.Material;
import ma.org.comfybackend.security.Repositories.CategoryRepository;
import ma.org.comfybackend.security.Repositories.PhotosRepository;
import ma.org.comfybackend.security.Repositories.ProductRepository;
import ma.org.comfybackend.security.Services.AccountService;
import ma.org.comfybackend.security.Services.ProductsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class ComfyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComfyBackendApplication.class, args);

    }
    @Bean
    PasswordEncoder passwordEncodor(){
        return new BCryptPasswordEncoder();
    }
    /* @Bean
     CommandLineRunner start(AccountService accountService) {
         return args -> {
             AppUser u = new AppUser();
             u.setName("dina");
             u.setPassword("dina");
             u.setRole("user");
             u.setEmail("dina.gmail");
             accountService.addNewUser(u);
         };
     }
           Customer c = new Customer();
            c.setName("customer");
            c.setPassword("dkndk");
            c.setRole("customer");
            c.setEmail("ldfjld@dljd.gmail");
            c.setBirthday("23/5/2000");
            accountService.addNewUser(u);
            accountService.addNewUser(c);

        };
    }*/
    /*@Bean
    CommandLineRunner start(ProductsService productsService, CategoryRepository categoryRepository, PhotosRepository photosRepository) {
        return args -> {
            //contain some errors because the collection of photos should be affected to just one product and also the image is its that should add the product as a foreign key and not the product add the collection of images
            Photos ph1 = new Photos("ckndknc");
            photosRepository.save(ph1);
            Photos ph2 = new Photos("ckndknc");
            photosRepository.save(ph2);
            Photos ph3 = new Photos("ckndknc");
            photosRepository.save(ph3);

            Collection<Photos> ph = new ArrayList<>();
            ph.add(ph1);
            ph.add(ph2);
            ph.add(ph3);

            Category c = new Category();
            c.setCollection("Living Room");
            c.setTitle("sofas");
            c.setImageC("dsdscsd");
            categoryRepository.save(c);

            Category d = new Category();
            c.setCollection("Living Room");
            c.setTitle("Bookcases");
            c.setImageC("dsdscsd");
            categoryRepository.save(d);

            Product u = new Product();
            u.setCategory(c);
            u.setDescription("dcsdllms,vmdl");
            u.setColor(Color.Blue);
            u.setImages(ph);
            u.setMaterial(Material.aluminum);
            u.setPrice(122);
            u.setQteStock(10);
            u.setPromotion(0);
            u.setCreated_at(new Date());

            Product P2 = new Product();
            P2.setCategory(c);
            P2.setDescription("dcsdllms,vmdl");
            P2.setColor(Color.Black);
            P2.setImages(ph);
            P2.setMaterial(Material.cork);
            P2.setPrice(322);
            P2.setQteStock(9);
            P2.setPromotion(30);
            P2.setCreated_at(new Date());

            Product up = new Product();
            up.setCategory(d);
            up.setDescription("sddcsdllms,vmdl");
            up.setColor(Color.Gold);
            up.setImages(ph);
            up.setMaterial(Material.corian);
            up.setPrice(622);
            up.setQteStock(30);
            up.setPromotion(0);
            up.setCreated_at(new Date());

            Product P3 = new Product();
            P3.setCategory(d);
            P3.setDescription("dcsdllmsDCSD,vmdl");
            P3.setColor(Color.Beige);
            P3.setImages(ph);
            P3.setMaterial(Material.bamboo);
            P3.setPrice(522);
            P3.setQteStock(9);
            P3.setPromotion(20);
            P3.setCreated_at(new Date());

            productsService.addNewProduct(u);
            productsService.addNewProduct(P2);
            productsService.addNewProduct(P3);
            productsService.addNewProduct(up);
        };
    }*/


}

