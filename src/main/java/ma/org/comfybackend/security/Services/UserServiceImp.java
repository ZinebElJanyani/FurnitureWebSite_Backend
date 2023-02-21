package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.Entities.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserServiceImp implements UserDetailsService {
    private AccountService accountService;

    public UserServiceImp(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByEmail(username);
        Collection<GrantedAuthority> authorities =new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole()));

        return new User(appUser.getName(),appUser.getPassword(),authorities);
    }
}
