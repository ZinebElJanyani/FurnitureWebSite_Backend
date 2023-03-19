package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.CaddyDTO;
import ma.org.comfybackend.security.Entities.Caddy;
import ma.org.comfybackend.security.Entities.Customer;
import ma.org.comfybackend.security.Entities.Item;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Repositories.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CaddyServiceIMP implements CaddyService{

    CaddyRepository caddyRepository;
    CustomerRepository customerRepository;
    ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    public CaddyServiceIMP(CaddyRepository caddyRepository,CustomerRepository customerRepository,
                           CategoryRepository categoryRepository,ProductRepository productRepository,
                           ItemRepository itemRepository) {
        this.caddyRepository = caddyRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
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

    @Override
    public int createItem(int customerId, int productId, int quantity) {


        Product p = productRepository.findById(productId).orElse(null);
        Item item =itemRepository.findByProduct(p);
        if(item!=null) {

            int oldQ = item.getQuantity() + quantity;
            if (oldQ < 0) {
                return 0;
            } else if (oldQ > p.getQteStock()) {
                return 1;

            } else {
                item.setQuantity(oldQ);
                itemRepository.save(item);
            }

        }else {

            item = new Item();
            Customer c = customerRepository.findById( customerId).orElse(null);
            if(c!=null) {

                Caddy caddy = c.getCaddy();

                item.setQuantity(quantity);
                item.setCaddy(caddy);
              //  c.setCaddy(caddy);
                item.setProduct(p);
               // p.setItems(item);
                itemRepository.save(item);
            }
        }

        return 2 ;
    }

    @Override
    public List<Item> showItems(int customerId) {
        Customer customer = customerRepository.findById( customerId).orElse(null);
        Caddy caddy = caddyRepository.findByCustomer(customer);

        return (List<Item>)caddy.getItems();
    }

    @Override
    public int deleteItem(int productId) {

        Product p = productRepository.findById(productId).orElse(null);
        Item item = itemRepository.findByProduct(p);
        int id = item.getId();
        itemRepository.delete(item);
        return id;
    }

    @Override
    public int updateCaddy(CaddyDTO caddyDTO, int idCustomer) {
        Customer customer = customerRepository.findById(idCustomer).orElse(null);
        Caddy caddy = caddyRepository.findByCustomer(customer);
        if(caddy!=null){
            caddy.setCoupon(caddyDTO.getCoupon());
            caddy.setDeliveryPrice(caddyDTO.getDeliveryPrice());
            caddy.setTotalPrice(caddyDTO.getTotalPrice());
            caddyRepository.save(caddy);
        }
        return idCustomer;
    }

}
