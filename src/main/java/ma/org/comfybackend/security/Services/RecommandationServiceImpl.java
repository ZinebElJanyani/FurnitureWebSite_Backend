package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Mappers.ProductMapper;
import ma.org.comfybackend.security.Repositories.ProductRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RecommandationServiceImpl implements RecommandationService {
    private RestTemplate restTemplate = new RestTemplate();
    private String baseUrl = "http://localhost:5000/recommendations/";
    private ProductMapper productMapper;
    private ProductRepository productRepository;

    RecommandationServiceImpl(ProductRepository productRepository){
        this.productMapper = new ProductMapper();
        this.productRepository = productRepository;

    }
    @Override
    public List<ProductDTO> listRecommandedProducts(int idCustomer) {

        String url = baseUrl + idCustomer;
        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>(){});
        List<String> recommendedProducts = response.getBody();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(String s:recommendedProducts){
            System.out.println(s+"Integer.valueOf(s) ="+Integer.valueOf(s));
            /*ça ne marche pas juste parceque j'ai utiliser des id des produits qui n'existe pas
           /* Product product = this.productRepository.findById(Integer.valueOf(s)).orElse(null);
        productDTOS.add(this.productMapper.fromProduct(product));*/
        }

        return productDTOS;
    }
}
