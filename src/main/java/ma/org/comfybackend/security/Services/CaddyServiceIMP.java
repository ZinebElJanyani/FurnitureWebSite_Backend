package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.CaddyDTO;
import ma.org.comfybackend.security.DTO.ItemDTO;
import ma.org.comfybackend.security.Entities.*;
import ma.org.comfybackend.security.Mappers.CaddyMapper;
import ma.org.comfybackend.security.Mappers.ItemMapper;
import ma.org.comfybackend.security.Mappers.ProductMapper;
import ma.org.comfybackend.security.Repositories.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CaddyServiceIMP implements CaddyService{

    CaddyRepository caddyRepository;
    CustomerRepository customerRepository;
    ProductRepository productRepository;
    CaddyMapper caddyMapper;
    CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    ItemMapper itemMapper;
    private ProductMapper productMapper;

    public CaddyServiceIMP(CouponRepository couponRepository,CaddyRepository caddyRepository,CustomerRepository customerRepository,
                           CategoryRepository categoryRepository,ProductRepository productRepository,
                           ItemRepository itemRepository) {
        this.caddyRepository = caddyRepository;
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.itemRepository = itemRepository;
        this.itemMapper = new ItemMapper();
        this.productMapper = new ProductMapper();
        this.caddyMapper = new CaddyMapper();
        this.couponRepository = couponRepository;
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
    public List<ItemDTO> showItems(int customerId) {
        Customer customer = customerRepository.findById( customerId).orElse(null);
        Caddy caddy = caddyRepository.findByCustomer(customer);
        List<Item> items = (List<Item>)caddy.getItems();
        List<ItemDTO> collect = items.stream().map(p -> this.itemMapper.fromItem(p)).collect(Collectors.toList());
        return collect;
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

    @Override
    public CaddyDTO showCady(int customerId) {
        System.out.println("loloh");
        Customer customer = customerRepository.findById( customerId).orElse(null);
        Caddy caddy = caddyRepository.findByCustomer(customer);
        return this.caddyMapper.fromCaddy(caddy);
    }

    @Override
    public List<ItemDTO> displayItems(int cid) {
      Customer c = customerRepository.findById(cid).orElse(null);
      Caddy caddy = caddyRepository.findByCustomer(c);

        List<Item> items = itemRepository.findByCaddy(caddy);
        List<ItemDTO> itemDTOS=new ArrayList<>();

        for(Item item : items){
           ItemDTO itemDTO = new ItemDTO();
           itemDTO.setPrice(item.getPrice());
           itemDTO.setQuantity(item.getQuantity());
           itemDTO.setProduct(productMapper.fromProduct(item.getProduct()));
           itemDTOS.add(itemDTO);
        }


        return itemDTOS;
    }

    @Override
    public float getCouponD(String code) {
        Coupon c = this.couponRepository.findByCode(code);
        float p;
        if(c!=null){
             p= c.getPrice();
        }
        else {
            p= 0L;
        }
        return p;
    }

}
