package ma.org.comfybackend.security.Folters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import ma.org.comfybackend.security.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.tools.jconsole.JConsole;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       if(request.getServletPath().equals("/api/UserAccount/refreshToken")||request.getServletPath().equals("/api/UserAccount/register")||request.getServletPath().equals("/api/UserAccount/usersRegistred")||request.getServletPath().equals("/api/products/show")){
           filterChain.doFilter(request,response);

       }else{
        String authorizationToken = request.getHeader(JWTUtil.AUTH8HEADER);
        if(authorizationToken!=null && authorizationToken.startsWith("Bearer ")){
            try {
                String jwt = authorizationToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJwt = jwtVerifier.verify(jwt);
               //sessionUtilisateur
                String userName = decodedJwt.getSubject();
                String[] roles = decodedJwt.getClaim("roles").asArray(String.class);

                Collection<GrantedAuthority> authorities =new ArrayList<>();
                for(String r:roles){
                    authorities.add(new SimpleGrantedAuthority(r));
                }

                //stocjer des ifos sur le user authentifier
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,null,authorities );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //fin du ce filtrer et passage à un autre filter
                filterChain.doFilter(request,response);

            }catch (Exception e){
                response.setHeader("Error Message",e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                System.out.println("koko");
            }
            
        }else {
            filterChain.doFilter(request,response);
        }
       }
    }
}
