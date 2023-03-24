package ma.org.comfybackend;

import ma.org.comfybackend.security.Entities.*;
import ma.org.comfybackend.security.Enumerations.Color;
import ma.org.comfybackend.security.Enumerations.Material;
import ma.org.comfybackend.security.Mappers.CustomerRegisterMapper;
import ma.org.comfybackend.security.Repositories.*;
import ma.org.comfybackend.security.Services.AccountService;
import ma.org.comfybackend.security.Services.ProductsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.tools.jconsole.JConsole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class ComfyBackendApplication {
    private final ProductRepository productRepository;
    private final CustomerRegisterMapper userMaper;
    public ComfyBackendApplication(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.userMaper = new CustomerRegisterMapper();
    }

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
             u.setPassword("123");
             u.setRole("user");
             u.setEmail("dina_fthi@gmail.com");

             accountService.addNewUserr(u);

             Customer c = new Customer();
             c.setName("najwa");
             c.setPassword("123");
             c.setRole("customer");
             c.setEmail("najwa.sadik@gmail.com");
             c.setBirthday("23/5/2000");
             accountService.addNewCustomer(userMaper.fromCustomer_CDTO(c));

         };
     }*/

    /*@Bean
     CommandLineRunner start(AccountService accountService) {
         return args -> {
             AppUser u = new AppUser();
             u.setName("dina");
             u.setPassword("123");
             u.setRole("user");
             u.setEmail("dina_fthi@gmail.com");

             accountService.addNewUserr(u);

             Customer c = new Customer();
             c.setName("sara");
             c.setPassword("123");
             c.setRole("customer");
             c.setEmail("sara.fadili@gmail.com");
             c.setBirthday("23/5/2000");
             accountService.addNewCustomer(userMaper.fromCustomer_CDTO(c));

         };
     }
*/


    /*@Bean
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
   /* @Bean
    CommandLineRunner start(RegionRepository regionRepository,CityRepository cityRepository) {
        return args -> {
            Region r = new Region();
            r.setNom("Tanger-Tetouan-Al Hoceima");

            Region r2 = new Region();
            r2.setNom("Oriental ");

            Region r3 = new Region();
            r3.setNom("Fes-Meknes ");

            Region r4 = new Region();
            r4.setNom("Rabat-Sale-Kenitra");

            Region r5 = new Region();
            r5.setNom("Beni Mellal-Khenifra");

            Region r6 = new Region();
            r6.setNom("Casablanca-Settat ");

            Region r7 = new Region();
            r7.setNom("Marrakech-Safi");

            Region r8 = new Region();
            r8.setNom("Souss-Massa ");

            regionRepository.save(r);
            regionRepository.save(r2);
            regionRepository.save(r3);
            regionRepository.save(r4);
            regionRepository.save(r5);
            regionRepository.save(r6);
            regionRepository.save(r7);
            regionRepository.save(r8);

            City c = new City();
            c.setNom("Tangier");
            c.setRegion(r);
            City c1 = new City();
            c1.setNom("Tetouan");
            c1.setRegion(r);
            City c2 = new City();
            c2.setNom("Al Hoceima");
            c2.setRegion(r);

            City c3 = new City();
            c3.setNom("Oujda");
            c3.setRegion(r2);
            City c4 = new City();
            c4.setNom("Nador");
            c4.setRegion(r2);
            City c5 = new City();
            c5.setNom("Berkane");
            c5.setRegion(r2);

            City c6 = new City();
            c6.setNom("Fez");
            c6.setRegion(r3);
            City c7 = new City();
            c7.setNom("Meknes");
            c7.setRegion(r3);
            City c8 = new City();
            c8.setNom("Ifrane");
            c8.setRegion(r3);


            City c9 = new City();
            c9.setNom("Rabat");
            c9.setRegion(r4);
            City c11 = new City();
            c11.setNom("Sale");
            c11.setRegion(r4);
            City c22 = new City();
            c22.setNom("Kenitra");
            c22.setRegion(r4);
            City cDC = new City();
            cDC.setNom("Sidi Kacem");
            cDC.setRegion(r4);
            City cBG = new City();
            cBG.setNom("Sidi Slimane");
            cBG.setRegion(r4);


            City c33 = new City();
            c33.setNom("Khouribga");
            c33.setRegion(r5);
            City c44 = new City();
            c44.setNom("Beni Mellal");
            c44.setRegion(r5);
            City c55 = new City();
            c55.setNom("Khenifra");
            c55.setRegion(r5);


            City c66 = new City();
            c66.setNom("Settat");
            c66.setRegion(r6);
            City c77 = new City();
            c77.setNom("Casablanca");
            c77.setRegion(r6);
            City c88 = new City();
            c88.setNom("Mohammedia");
            c88.setRegion(r6);
            City cIT = new City();
            cIT.setNom("El Jadida");
            cIT.setRegion(r6);



            City c99 = new City();
            c99.setNom("Marrakech");
            c99.setRegion(r7);
            City c111 = new City();
            c111.setNom("Safi");
            c111.setRegion(r7);
            City c222 = new City();
            c222.setNom("Essaouira");
            c222.setRegion(r7);

            City c333 = new City();
            c333.setNom("Agadir");
            c333.setRegion(r8);
            City c444 = new City();
            c444.setNom("Inezgane");
            c444.setRegion(r8);
            City c555 = new City();
            c555.setNom("Taroudant");
            c555.setRegion(r8);

            cityRepository.save(c);
            cityRepository.save(c1);
            cityRepository.save(c2);
            cityRepository.save(c555);
            cityRepository.save(c444);
            cityRepository.save(c333);
            cityRepository.save(c222);
            cityRepository.save(c111);
            cityRepository.save(c99);
            cityRepository.save(c88);
            cityRepository.save(c77);
            cityRepository.save(c66);
            cityRepository.save(c55);
            cityRepository.save(c44);
            cityRepository.save(c33);
            cityRepository.save(c22);
            cityRepository.save(c11);
            cityRepository.save(c9);
            cityRepository.save(c8);
            cityRepository.save(c7);
            cityRepository.save(c6);
            cityRepository.save(c5);
            cityRepository.save(c4);
            cityRepository.save(c3);

            cityRepository.save(cDC);
            cityRepository.save(cIT);
            cityRepository.save(cBG);



        };
    }*/

}

