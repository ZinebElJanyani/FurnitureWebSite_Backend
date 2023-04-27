package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.ProductCDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.CommandItem;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Enumerations.CommandState;
import ma.org.comfybackend.security.Mappers.ProductMapper;
import ma.org.comfybackend.security.Repositories.CommandItemRepository;
import ma.org.comfybackend.security.Repositories.CommandRepository;
import ma.org.comfybackend.security.Repositories.CustomerRepository;
import ma.org.comfybackend.security.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChartServiceImpl implements ChartService{
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;

    private CommandRepository commandRepository;
    private CommandItemRepository commandItemRepository;
    private ProductMapper productMapper;

    public ChartServiceImpl(ProductRepository productRepository, CustomerRepository customerRepository, CommandRepository commandRepository,CommandItemRepository commandItem) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.commandRepository = commandRepository;
        this.commandItemRepository = commandItem;
        this.productMapper = new ProductMapper();
    }

    @Override
    public Map<String, Object> statistics() {
        Long totalCustomers = this.customerRepository.count();
        Long totalProduct  = this.productRepository.count();
        Long totalOreders = this.commandRepository.count();

        Long mounthOrders = this.commandRepository.countCommandsInCurrentMonth();
        Long mounthProducts = this.productRepository.countProductsInCurrentMonth();
        Long mounthCustomers = this.customerRepository.countCustomersInCurrentMonth();
        Long totalProfits = this.commandRepository.countProfits(CommandState.cancelled);

        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);
        int previousMonth = previousMonthDate.getMonthValue();
        int year = previousMonthDate.getYear();

        Long currentMonthSales = this.commandRepository.currentMounthProfits(CommandState.cancelled);
        Long previousMonthSales = this.commandRepository.previousMounthProfits(CommandState.cancelled, previousMonth, year);
        double percentageChange = ((currentMonthSales - previousMonthSales) / (double)previousMonthSales) * 100;

        Map<String, Object> sta = new HashMap<>();
        sta.put("totalCustomers", totalCustomers);
        sta.put("totalProduct", totalProduct);
        sta.put("totalOreders", totalOreders);
        sta.put("mounthOrders", mounthOrders);
        sta.put("mounthProducts", mounthProducts);
        sta.put("mounthCustomers", mounthCustomers);
        sta.put("totalProfits", totalProfits);
        sta.put("percentageSales", percentageChange);


        return sta;

    }

    @Override
    public Map<String,Object>  getMostOrdered() {
        List<Product> products = this.productRepository.findByDeletedIsFalse();

        Long maxTotalOrders=0L;
        Product p=new Product();

       for(Product pr: products){

           Long count =  this.commandItemRepository.countCommandsForProduct(pr);

          if(count>maxTotalOrders){
            maxTotalOrders = count;

            p=pr;
          }
       }
        ProductDTO productDTO = this.productMapper.fromProduct(p);

        Map<String, Object> result = new HashMap<>();
        result.put("product", productDTO);
        result.put("totalOrders", maxTotalOrders);
        return result;
    }
}
