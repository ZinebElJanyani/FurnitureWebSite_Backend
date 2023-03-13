package ma.org.comfybackend.security.Mappers;

import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Customer;
import org.springframework.beans.BeanUtils;

public class CustomerRegisterMapper {
    public Customer fromCustomerRegisterDTO_C(CustomerRegisterDTO customerRegisterDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRegisterDTO,customer);
        return customer;
    }
    public CustomerRegisterDTO fromCustomer_CDTO(Customer c){
        CustomerRegisterDTO customerDTO = new CustomerRegisterDTO();
        BeanUtils.copyProperties(c,customerDTO);
        return customerDTO;
    }
    public AppUser fromCustomerRegisterDTO_U(CustomerRegisterDTO customerRegisterDTO){
        AppUser user = new AppUser();
        BeanUtils.copyProperties(customerRegisterDTO,user);
        return user;
    }
}
