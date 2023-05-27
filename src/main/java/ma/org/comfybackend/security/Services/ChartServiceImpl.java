package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.Command;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Entities.Review;
import ma.org.comfybackend.security.Enumerations.CommandState;
import ma.org.comfybackend.security.Mappers.ProductMapper;
import ma.org.comfybackend.security.Repositories.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class ChartServiceImpl implements ChartService{
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private CategoryRepository categoryRepository;
    private CommandRepository commandRepository;
    private CommandItemRepository commandItemRepository;
    private ReviewRepository reviewRepository;
    private ProductMapper productMapper;

    public ChartServiceImpl(ReviewRepository reviewRepository,CategoryRepository categoryRepository,ProductRepository productRepository, CustomerRepository customerRepository, CommandRepository commandRepository,CommandItemRepository commandItem) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.commandRepository = commandRepository;
        this.commandItemRepository = commandItem;
        this.productMapper = new ProductMapper();
        this.categoryRepository = categoryRepository;
        this.reviewRepository=reviewRepository;
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

        Long currentMonthSales = (this.commandRepository.currentMounthProfits(CommandState.cancelled)) !=null ? this.commandRepository.currentMounthProfits(CommandState.cancelled) :0;
        Long previousMonthSales = this.commandRepository.previousMounthProfits(CommandState.cancelled, previousMonth, year);
       double percentageChange = ((currentMonthSales - previousMonthSales) / (double)previousMonthSales) * 100;
        System.out.println(" previousMonth:"+previousMonth+" currentMonthSales:"+currentMonthSales+" previousMonthSales:"+previousMonthSales);

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

    @Override
    public Map<String, Long> getSalesPerCategory() {
        System.out.println("koko");
        Map<String, Long> salesCat = new HashMap<>();
        List<Category> categories = this.categoryRepository.findAll();
        String categoryName;
        for(Category cat : categories){
            Long  countSales= 0L;
            categoryName = cat.getTitle();
            for(Product pr:cat.getProducts()){
                countSales += this.commandItemRepository.countCommandsForProduct(pr);
            }
            salesCat.put(categoryName,countSales);
        }

        return salesCat;
    }

    @Override
    public List<Long> getCountRating() {
        List<Review>reviews =  this.reviewRepository.findAll();
        List<Long>result=new ArrayList<>(Collections.nCopies(5, 0L));
        for(int i=0;i< reviews.size();i++){
            if(reviews.get(i).getNbre_etoile()==1){
                result.set(0,(result.get(0)+1));
            } else if (reviews.get(i).getNbre_etoile()==2) {
                result.set(1,(result.get(1)+1));
            }else if (reviews.get(i).getNbre_etoile()==3) {
                result.set(2,(result.get(2)+1));
            }else if (reviews.get(i).getNbre_etoile()==4) {
                result.set(3,(result.get(3)+1));
            }else if (reviews.get(i).getNbre_etoile()==5) {
                result.set(4,(result.get(4)+1));
            }
        }
        return result;
    }

    @Override
    public List<Long> getSalesPerMonths() {

        List<Command> commands = this.commandRepository.findAll();
        List<Long> result=new ArrayList<>(Collections.nCopies(12, 0L));

        for(int i=0;i< commands.size();i++){
            for(int j=0;j< 12;j++){
                if(commands.get(i).getDate().getMonth()==j){
                    result.set(j,(result.get(j)+1));
                    break;
                    }
                }
        }
        return result;
    }
}
