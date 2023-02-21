package ma.org.comfybackend;

import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}

