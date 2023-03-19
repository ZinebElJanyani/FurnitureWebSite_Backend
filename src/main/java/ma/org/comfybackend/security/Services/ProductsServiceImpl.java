package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.CollectionT;
import ma.org.comfybackend.security.Entities.Photos;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Mappers.ProductMapper;
import ma.org.comfybackend.security.Repositories.CategoryRepository;
import ma.org.comfybackend.security.Repositories.CollectionTRepository;
import ma.org.comfybackend.security.Repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@Service
@Transactional
public class ProductsServiceImpl implements ProductsService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private CollectionTRepository collectionTRepository;
    private ProductMapper productMapper;
    public ProductsServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, CollectionTRepository collectionTRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionTRepository = collectionTRepository;
        this.productMapper = new ProductMapper();
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
    public List<ProductDTO> listSelectedProducts(double min , double max) {

        List<Product> products = productRepository.findBySelectedIsTrueAndPriceBetween(min,max);
        List<ProductDTO> collect = products.stream().map(p -> this.productMapper.fromProduct(p)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<byte[]> listProductsPhotos(int id) throws IOException {
        //on peut untiliser ça: Customer customer = customerRepo.findById(customerID).orElse(null);
        Product p =productRepository.findById(id).get();
        List<byte[]> photos = new ArrayList<>();
        Photos[] imagesN= p.getImages().toArray(new Photos[p.getImages().size()]);
       System.out.println(imagesN.length);
        for(int i=0;i<imagesN.length;i++){

            photos.add(Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/homeDecor/products/"+imagesN[i].getImagePath())));
        }
        return photos;
    }

    @Override
    public byte[] listProductsFirstPhoto(int id) throws IOException {
        Product p =productRepository.findById(id).get();

        Photos[] imagesN= p.getImages().toArray(new Photos[p.getImages().size()]);

        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/homeDecor/products/"+imagesN[0].getImagePath()));

    }

    @Override
    public List<ProductDTO> listProductsByCatg(int id,double min , double max) {
        List<Product> products = productRepository.findByCategoryIdAndPriceBetween(id,min,max);
        List<ProductDTO> collect = products.stream().map(p -> this.productMapper.fromProduct(p)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public ProductDTO showOneProduct(int id) {
       ProductDTO pDTO = productMapper.fromProduct(productRepository.findById(id).orElse(null)) ;
        return pDTO;
    }

    @Override
    public List<ProductDTO> searchProduct(String name) {
        List<ProductDTO> collect = productRepository.findByNomContains(name).stream().map(p -> productMapper.fromProduct(p)).collect(Collectors.toList());
        return collect;
    }

}
