package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.CollectionT;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Repositories.CategoryRepository;
import ma.org.comfybackend.security.Repositories.CollectionTRepository;
import ma.org.comfybackend.security.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductsServiceImpl implements ProductsService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private CollectionTRepository collectionTRepository;
    public ProductsServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository,CollectionTRepository collectionTRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionTRepository = collectionTRepository;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addNewProduct(Product product) {

        return productRepository.save(product);
    }

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    @Override
        public List<CollectionT> listCollection() {
        return collectionTRepository.findAll();
    }

    @Override
    public List<Product> listSelectedProducts() {
        return productRepository.findBySelectedIsTrue();
    }

}
