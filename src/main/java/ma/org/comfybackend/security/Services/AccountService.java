package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.AppUserDTO;
import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface AccountService {
    AppUser addNewCustomer(CustomerRegisterDTO customerRegisterDTO);
    AppUser addNewUserr(AppUser user);

    CustomerRegisterDTO loadUserByUserName(String username);


    AppUser loadUserByEmail(String email);
    List<AppUser> listCustomers() ;

    List<Customer> showCustomerInfo();

    List<String> listEmailUsers();

    AppUser editCustomer(CustomerRegisterDTO customerRegisterDTO);

    void uploadImageUser(MultipartFile file, int idCustomer) throws IOException;

    byte[] getUserPhoto(int id) throws IOException;

    List<CustomerRegisterDTO> showCustomers();

    List<Integer> countCustomerCommands();

    List<Integer> countCustomerReviews();

    String findNameById(int id);

    AppUser editUser(AppUserDTO appUserDTO);

    void setLastConxTime(int id, Date lastTime);

    List<Integer> getNotifications(int id);
}
