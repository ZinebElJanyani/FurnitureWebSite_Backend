package ma.org.comfybackend.security.Services;

import java.util.List;

public interface RecommandationService {

    List<String> listRecommandedProducts(int idCustomer);
}
