package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductsServiceImpl implements ProductsService{
    private ProductRepository productRepository;
    public ProductsServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addNewProduct(Product product) {

        return productRepository.save(product);
    }
}
