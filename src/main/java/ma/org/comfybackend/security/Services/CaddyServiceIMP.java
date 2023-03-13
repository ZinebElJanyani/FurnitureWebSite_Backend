package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.Entities.Caddy;
import ma.org.comfybackend.security.Entities.Customer;
import ma.org.comfybackend.security.Repositories.CaddyRepository;
import ma.org.comfybackend.security.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CaddyServiceIMP implements CaddyService{

    CaddyRepository caddyRepository;
    CustomerRepository customerRepository;
    public CaddyServiceIMP(CaddyRepository caddyRepository,CustomerRepository customerRepository) {
        this.caddyRepository = caddyRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Caddy createCaddy(String customerEmail){

        Caddy c = new Caddy();
        Customer customer = customerRepository.findByEmail(customerEmail);
        if(customer.getCaddy()==null){
        c.setCustomer(customer);
        customer.setCaddy(c);
        customerRepository.save(customer);
        return caddyRepository.save(c);
        }
        return null;
    }
}
