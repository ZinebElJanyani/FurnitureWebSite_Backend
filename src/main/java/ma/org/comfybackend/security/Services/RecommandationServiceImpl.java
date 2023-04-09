package ma.org.comfybackend.security.Services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RecommandationServiceImpl implements RecommandationService {
    private RestTemplate restTemplate = new RestTemplate();
    private String baseUrl = "http://localhost:5000/recommendations/";
    @Override
    public List<String> listRecommandedProducts(int idCustomer) {

        String url = baseUrl + idCustomer;
        ResponseEntity<List<String>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>(){});
        List<String> recommendedProducts = response.getBody();

        return recommendedProducts;
    }
}
