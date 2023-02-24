package ma.org.comfybackend;

import ma.org.comfybackend.security.Entities.*;
import ma.org.comfybackend.security.Enumerations.Color;
import ma.org.comfybackend.security.Enumerations.Material;
import ma.org.comfybackend.security.Repositories.CategoryRepository;
import ma.org.comfybackend.security.Repositories.CollectionTRepository;
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
  /*  @Bean
    CommandLineRunner start(ProductsService productsService, CategoryRepository categoryRepository, PhotosRepository photosRepository, CollectionTRepository collectionTRepository) {
        return args -> {
            //contain some errors because the collection of photos should be affected to just one product and also the image is its that should add the product as a foreign key and not the product add the collection of images



            CollectionT collectionT = new CollectionT();
            collectionT.setTitle("Living Room");
            collectionT.setImageC("dsdscsd");
            collectionTRepository.save(collectionT);

            CollectionT collectionT2 = new CollectionT();
            collectionT2.setTitle("Bedroom");
            collectionT2.setImageC("dsdscsd");
            collectionTRepository.save(collectionT2);

            CollectionT collectionT3 = new CollectionT();
            collectionT3.setTitle("Office");
            collectionT3.setImageC("dsdscsd");
            collectionTRepository.save(collectionT3);

            CollectionT collectionT4 = new CollectionT();
            collectionT4.setTitle("Dining Room");
            collectionT4.setImageC("dsdscsd");
            collectionTRepository.save(collectionT4);

            CollectionT collectionT5 = new CollectionT();
            collectionT5.setTitle("Kids Room");
            collectionT5.setImageC("dsdscsd");
            collectionTRepository.save(collectionT5);

            CollectionT collectionT6 = new CollectionT();
            collectionT6.setTitle("Outdoor");
            collectionT6.setImageC("dsdscsd");
            collectionTRepository.save(collectionT6);


            Category d = new Category();
            d.setTitle("sofas");
            d.setCollection(collectionT);
            categoryRepository.save(d);

            Category d1 = new Category();
            d1.setTitle("Bookcases");
            d1.setCollection(collectionT);
            categoryRepository.save(d1);

            Category d2 = new Category();
            d2.setTitle("TV Stands");
            d2.setCollection(collectionT);
            categoryRepository.save(d2);

            Product u = new Product();
            u.setCategory(d);
            u.setDescription("dcsdllms,vmdl");
            u.setColor(Color.Blue);

            u.setMaterial(Material.aluminum);
            u.setPrice(122);
            u.setQteStock(10);
            u.setPromotion(0);
            u.setSelected(true);
            u.setCreated_at(new Date());

            Product P2 = new Product();
            P2.setCategory(d);
            P2.setDescription("dcsdllms,vmdl");
            P2.setColor(Color.Black);

            P2.setMaterial(Material.cork);
            P2.setPrice(322);
            P2.setQteStock(9);
            P2.setSelected(true);
            P2.setPromotion(30);
            P2.setCreated_at(new Date());

            Product up = new Product();
            up.setCategory(d);
            up.setDescription("sddcsdllms,vmdl");
            up.setColor(Color.Gold);

            up.setMaterial(Material.corian);
            up.setPrice(622);
            up.setQteStock(30);
            up.setSelected(true);
            up.setPromotion(0);
            up.setCreated_at(new Date());

            productsService.addNewProduct(u);
            productsService.addNewProduct(P2);
            productsService.addNewProduct(P3);
            productsService.addNewProduct(up);
            Photos ph1 = new Photos("ckndknc");
            ph1.setProduct(u);
            photosRepository.save(ph1);

            Photos ph2 = new Photos("ckndknc");
            ph2.setProduct(u);
            photosRepository.save(ph2);

            Photos ph3 = new Photos("ckndknc");
            ph3.setProduct(u);
            photosRepository.save(ph3);

            Photos ph11 = new Photos("ckndknc");
            ph11.setProduct(P2);
            photosRepository.save(ph11);
            Photos ph22 = new Photos("ckndknc");
            ph22.setProduct(P2);
            photosRepository.save(ph22);
            Photos ph33 = new Photos("ckndknc");
            ph33.setProduct(P2);
            photosRepository.save(ph33);

            Photos ph111 = new Photos("ckndknc");
            ph111.setProduct(up);
            photosRepository.save(ph111);
            Photos ph222 = new Photos("ckndknc");
            ph222.setProduct(up);
            photosRepository.save(ph222);
            Photos ph333 = new Photos("ckndknc");
            ph333.setProduct(up);
            photosRepository.save(ph333);
           /* Product P3 = new Product();
            P3.setCategory(d);
            P3.setDescription("dcsdllmsDCSD,vmdl");
            P3.setColor(Color.Beige);
            P3.setImages(ph);
            P3.setMaterial(Material.bamboo);
            P3.setPrice(522);
            P3.setQteStock(9);
            P3.setPromotion(20);
            P3.setCreated_at(new Date());


        };
    }*/


}

