package ma.org.comfybackend.security.Mappers;

import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Customer;
import ma.org.comfybackend.security.Entities.Product;
import org.springframework.beans.BeanUtils;

public class ProductMapper {
    public Product fromProductDTO(ProductDTO productDTO){
        Product p = new Product();
        BeanUtils.copyProperties(productDTO,productDTO);
        return p;
    }
    public ProductDTO fromProduct(Product product){
        ProductDTO pdto = new ProductDTO();
        BeanUtils.copyProperties(product,pdto);
        return pdto;
    }
}
