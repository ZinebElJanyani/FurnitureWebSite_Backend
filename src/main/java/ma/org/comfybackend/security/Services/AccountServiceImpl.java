package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Customer;
import ma.org.comfybackend.security.Mappers.CustomerRegisterMapper;
import ma.org.comfybackend.security.Repositories.AppUserRepository;
import ma.org.comfybackend.security.Repositories.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository userRepositiry;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;
    private CustomerRegisterMapper customerRegisterMapper;
    public AccountServiceImpl(AppUserRepository userRepositiry, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.userRepositiry = userRepositiry;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRegisterMapper = new CustomerRegisterMapper();
    }

    @Override
    public AppUser addNewUser(CustomerRegisterDTO customerRegisterDTO) {
        String pass=customerRegisterDTO.getPassword();
        customerRegisterDTO.setPassword(passwordEncoder.encode(pass));
        if(customerRegisterDTO.getRole().equals("customer")){
            Customer customer = customerRegisterMapper.fromCustomerRegisterDTO_C(customerRegisterDTO);
             return userRepositiry.save(customer);}
        else{
           /* AppUser user2=new AppUser();
            BeanUtils.copyProperties(user,user2);
            return userRepositiry.save(user2);*/
            AppUser user = customerRegisterMapper.fromCustomerRegisterDTO_U(customerRegisterDTO);
            return userRepositiry.save(user);
        }
    }


    @Override
    public AppUser loadUserByUserName(String username) {
        return userRepositiry.findByName(username);
    }
    @Override
    public AppUser loadUserByEmail(String email) {
        return userRepositiry.findByEmail(email);
    }
    @Override
    public List<AppUser> listCustomers() {
        List<AppUser> collect = userRepositiry.findAll().stream().filter(u -> u instanceof Customer).collect(Collectors.toList());

        return collect;

    }

    @Override
    public List<Customer> showCustomerInfo() {
        return customerRepository.findAll();
    }

    @Override
    public List<String> listEmailUsers() {
        return userRepositiry.findAllEmail();
    }


}
