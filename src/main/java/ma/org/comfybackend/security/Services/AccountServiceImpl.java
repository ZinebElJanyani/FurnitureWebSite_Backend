package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.AppUserDTO;
import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.DTO.ItemDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Command;
import ma.org.comfybackend.security.Entities.Customer;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Mappers.CustomerRegisterMapper;
import ma.org.comfybackend.security.Repositories.AppUserRepository;
import ma.org.comfybackend.security.Repositories.CommandRepository;
import ma.org.comfybackend.security.Repositories.CustomerRepository;
import ma.org.comfybackend.security.Repositories.ProductRepository;
import org.apache.tika.Tika;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository userRepositiry;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private  CommandRepository commandRepository;

    private PasswordEncoder passwordEncoder;
    private CustomerRegisterMapper customerRegisterMapper;
    public AccountServiceImpl(CommandRepository commandRepository,ProductRepository productRepository,AppUserRepository userRepositiry, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.userRepositiry = userRepositiry;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRegisterMapper = new CustomerRegisterMapper();
        this.productRepository = productRepository;
        this.commandRepository = commandRepository;
    }

    @Override
    public AppUser addNewCustomer(CustomerRegisterDTO customerRegisterDTO) {
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
    public AppUser addNewUserr(AppUser user) {
        String pass=user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));
            return userRepositiry.save(user);
    }

    @Override
    public CustomerRegisterDTO loadUserByUserName(String username) {
        Customer customer = customerRepository.findByName(username);
        CustomerRegisterDTO customerRegisterDTO;
        if(customer !=null) {
            customerRegisterDTO  =customerRegisterMapper.fromCustomer_CDTO(customer);
        }else {
            AppUser user = this.userRepositiry.findByName(username);
            customerRegisterDTO = customerRegisterMapper.fromUser(user);

        }

        System.out .println(customerRegisterDTO.getId());
        System.out .println(customerRegisterDTO.getEmail());

        return customerRegisterDTO;
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

    @Override
    public AppUser editCustomer(CustomerRegisterDTO customerRegisterDTO) {

        Customer customer = customerRepository.findById(customerRegisterDTO.getId()).orElse(null);
        customer.setName(customerRegisterDTO.getName());
        customer.setEmail(customerRegisterDTO.getEmail());
        customer.setPhone(customerRegisterDTO.getPhone());
        customer.setBirthday(customerRegisterDTO.getBirthday());

        Customer c = this.customerRepository.save(customer);

        return c;
    }

    @Override
    public void uploadImageUser(MultipartFile file, int idCustomerorUser) throws IOException {
        Customer customer = customerRepository.findById(idCustomerorUser).orElse(null);
        String contentType = new Tika().detect(file.getInputStream());
        String extension = "";

        // Set the extension based on the detected content type
        if (contentType.equals("image/jpeg")) {
            extension = "jpg";
        } else if (contentType.equals("image/png")) {
            extension = "png";
        } else {
            throw new IOException("Unsupported image format");
        }
        if(customer!=null) {
            // Delete the existing image file
            String oldFilePath = System.getProperty("user.home") + "/homeDecor/userImages/" + customer.getPhotoPath();
            Files.deleteIfExists(Paths.get(oldFilePath));

            customer.setPhotoPath(idCustomerorUser + "." + extension);
            try {
                Files.write(Paths.get(System.getProperty("user.home") + "/homeDecor/userImages/" + customer.getPhotoPath()), file.getBytes());
                customerRepository.save(customer);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{

            AppUser user = userRepositiry.findById(idCustomerorUser).orElse(null);
            String oldFilePath = System.getProperty("user.home") + "/homeDecor/userImages/" + user.getPhotoPath();
            Files.deleteIfExists(Paths.get(oldFilePath));

            user.setPhotoPath(idCustomerorUser + "." + extension);
            try {
                Files.write(Paths.get(System.getProperty("user.home") + "/homeDecor/userImages/" + user.getPhotoPath()), file.getBytes());
                this.userRepositiry.save(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public byte[] getUserPhoto(int id) throws IOException {
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer!=null && !customer.getPhotoPath().equals("")){
            byte[] result = Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/homeDecor/userImages/"+customer.getPhotoPath()));
            return result;
        }else{
            AppUser user = userRepositiry.findById(id).orElse(null);
                System.out.println(user.getPhotoPath());
            if(user!=null && !user.getPhotoPath().equals("")){
                byte[] result = Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/homeDecor/userImages/"+user.getPhotoPath()));
                return result;
            }
        }

        return null;

    }

    @Override
    public List<CustomerRegisterDTO> showCustomers() {
        List<Customer>  customers = this.customerRepository.findAll();
        List<CustomerRegisterDTO> collect = customers.stream().map(c -> this.customerRegisterMapper.fromCustomer_CDTO(c)).collect(Collectors.toList());


        return collect;
    }

    @Override
    public List<Integer> countCustomerCommands() {
        List<Customer> customers = this.customerRepository.findAll();
        List<Integer> result = new ArrayList<>();
        for(Customer c : customers){
            result.add(c.getCommands().size());
        }
        return result;
    }

    @Override
    public List<Integer> countCustomerReviews() {
        List<Customer> customers = this.customerRepository.findAll();
        List<Integer> result = new ArrayList<>();
        for(Customer c : customers){
            result.add(c.getReviews().size());
        }
        return result;

    }

    @Override
    public String findNameById(int id) {
        return this.customerRepository.findById(id).orElse(null).getName();
    }

    @Override
    public AppUser editUser(AppUserDTO appUserDTO) {
        AppUser user = userRepositiry.findById(appUserDTO.getId()).orElse(null);
        user.setName(appUserDTO.getName());
        user.setEmail(appUserDTO.getEmail());
        user.setPhone(appUserDTO.getPhone());

        AppUser c = this.userRepositiry.save(user);

        return c;
    }

    @Override
    public void setLastConxTime(int id, Date lastTime) {
        AppUser user = userRepositiry.findById(id).orElse(null);
        user.setUpdated_at(lastTime);
        System.out.println(user.getUpdated_at());
        userRepositiry.save(user);
    }

    @Override
    public List<Integer> getNotifications(int id) {
        AppUser user = this.userRepositiry.findById(id).orElse(null);
    List<Product>  outOfStocks = this.productRepository.findByQteStockEquals(0);
    List<Command> newCommands = this.commandRepository.findByDateAfter(user.getUpdated_at());
    Command c = this.commandRepository.findFirstByOrderByDateDesc();

        Long durationMillis = (new Date()).getTime()-c.getDate().getTime();
        Duration duration = Duration.ofMillis(durationMillis);
        int seconds = (int) duration.getSeconds();

    List<Integer> counts = new ArrayList<>();
    counts.add(outOfStocks.size());
    counts.add(newCommands.size());
        counts.add(seconds);

        return counts;
    }


}
