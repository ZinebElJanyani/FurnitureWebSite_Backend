package ma.org.comfybackend.security.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Customer;
import ma.org.comfybackend.security.JWTUtil;
import ma.org.comfybackend.security.Repositories.CustomerRepository;
import ma.org.comfybackend.security.Services.AccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

//http://localhost:8084/api/UserAccount/customers
@RestController
@RequestMapping("api/UserAccount")
public class AccountController {
    private AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/usersRegistred")
    public List<String> showUserRegistred(){
        return accountService.listEmailUsers();
    }



    @PostMapping(path = "/register")
    public AppUser registerUser(@RequestBody CustomerRegisterDTO customerRegisterDTO){
        return accountService.addNewCustomer(customerRegisterDTO);
    }

    @GetMapping(path="/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String authtoken = request.getHeader(JWTUtil.AUTH8HEADER);
        if(authtoken!=null && authtoken.startsWith("Bearer ")){
            try {
                String refreshJWT = authtoken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);

                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJwt = jwtVerifier.verify(refreshJWT);
                //sessionUtilisateur
                String userName = decodedJwt.getSubject();

                //chercher dans le blacklist
                AppUser user = accountService.loadUserByUserName(userName);
                ArrayList<String> roles = new ArrayList<>();
                roles.add(user.getRole());
                String jwtAccessToken = JWT.create()
                        .withSubject(user.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                        .withIssuer(request.getRequestURL().toString())

                        .withClaim("roles",roles)
                        .sign(algorithm);

                Map<String,String> idToken = new HashMap<>();
                idToken.put("acces-token",jwtAccessToken);
                idToken.put("refresh-token",refreshJWT);
                response.setContentType("application/json");
                //serialiser un objet sous format JSON
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);

            }catch (Exception e){
                throw e;
            }

        }else {
            throw new RuntimeException("Refresh token is required !");
            }
    }


    @GetMapping(path = "/profile")
    public AppUser showProfile(Principal principal){
    return accountService.loadUserByUserName(principal.getName());
    }
}


