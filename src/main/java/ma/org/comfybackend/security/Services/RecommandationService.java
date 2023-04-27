package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.ProductDTO;

import java.util.List;

public interface RecommandationService {

    List<ProductDTO> listRecommandedProducts(int idCustomer);
}
