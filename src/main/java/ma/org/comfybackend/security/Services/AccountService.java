package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Customer;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(CustomerRegisterDTO customerRegisterDTO);
    //Customer addNewCustomer(Customer customer);

    AppUser loadUserByUserName(String username);


    AppUser loadUserByEmail(String email);
    List<AppUser> listCustomers() ;

    List<Customer> showCustomerInfo();

    List<String> listEmailUsers();
}
