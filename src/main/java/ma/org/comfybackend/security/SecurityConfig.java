package ma.org.comfybackend.security;

import ma.org.comfybackend.security.Folters.JwtAuthentificationFilter;
import ma.org.comfybackend.security.Folters.JwtAuthorizationFilter;
import ma.org.comfybackend.security.Services.UserServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImp userServiceImp;

    public SecurityConfig(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImp);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();

        http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
            config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
            config.setAllowCredentials(true);
            return config;
        });
        http.headers().frameOptions().disable();
        //http.formLogin();

        //http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/UserAccount/save/**").hasAuthority("customer");

        http.authorizeHttpRequests()
                .antMatchers("/api/UserAccount/refreshToken/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/UserAccount/register/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/UserAccount/usersRegistred/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/show/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/categories/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/collections/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/selected_P/{min}/{max}/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/productP/{id}/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/product/{id}/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/productFirst_P/{id}/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/products/products_catg/{id}/**").permitAll()

                .anyRequest().authenticated();
       // http.authorizeHttpRequests().antMatchers("/api/UserAccount/login/**").permitAll().anyRequest().authenticated();




        http.addFilter(new JwtAuthentificationFilter(authenticationManagerBean()));
        // 1er filter = midlware
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
