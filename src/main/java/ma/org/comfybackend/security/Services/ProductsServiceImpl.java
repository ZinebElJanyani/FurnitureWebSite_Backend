package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.DTO.ReviewDTO;
import ma.org.comfybackend.security.Entities.*;
import ma.org.comfybackend.security.Mappers.ProductMapper;
import ma.org.comfybackend.security.Mappers.ReviewMapper;
import ma.org.comfybackend.security.Repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private ReviewMapper reviewMapper;
    private final CustomerRepository customerRepository;
    private ReviewRepository reviewRepository;
    private final RegionRepository regionRepository;

    public ProductsServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, CollectionTRepository collectionTRepository,
                               CustomerRepository customerRepository,ReviewRepository reviewRepository,
                               RegionRepository regionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionTRepository = collectionTRepository;
        this.productMapper = new ProductMapper();
        this.customerRepository = customerRepository;
        this.reviewMapper = new ReviewMapper();
        this.reviewRepository=reviewRepository;
        this.regionRepository = regionRepository;
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

    @Override
    public int createReview(ReviewDTO reviewDTO, int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Product product=productRepository.findById(productId).orElse(null);

        Review review = reviewMapper.fromReviewDTO(reviewDTO);
        review.setRecommanded(reviewDTO.getIsRecommanded());
        System.out.println(reviewDTO.getIsRecommanded());
        review.setProduct(product);
        review.setCustomer(customer);

        Review r = this.reviewRepository.save(review);
        return r.getId();
    }

    @Override
    public void uploadImageReview(MultipartFile file, int idReview) throws IOException {
        Review review =reviewRepository.findById(idReview).orElse(null);
        review.setImage(idReview+".jpg");
        Files.write(Paths.get(System.getProperty("user.home")+"/homeDecor/reviews/"+review.getImage()),file.getBytes());
        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDTO> listReviews(int idProduct) {
        Product p = productRepository.findById(idProduct).orElse(null);
        List <Review> reviews = reviewRepository.findByProduct(p);
        List<ReviewDTO> collect = reviews.stream().map(n -> reviewMapper.fromReview(n)).collect(Collectors.toList());

        for(int i=0;i<collect.size();i++){
            collect.get(i).setId_customer(reviews.get(i).getCustomer().getId());
        }
        return collect;
    }

    @Override
    public byte[] getRiviewPhoto(int id) throws IOException {

        Review review = reviewRepository.findById(id).orElse(null);
        if(!review.getImage().equals("")){
            byte[] result = Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/homeDecor/reviews/"+review.getImage()));
            return result;
        }

        return null;
    }

    @Override
    public int deleteReview(int idReview) {
        reviewRepository.deleteById(idReview);
        return idReview;
    }

    @Override
    public List<ProductDTO> listProducts(List<Integer> ids) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Integer i :ids){
            Product p = productRepository.findById(i).orElse(null);
            productDTOS.add(this.productMapper.fromProduct(p));
        }
        return productDTOS;
    }

    @Override
    public int addFavorite(List<Integer> ids, int idCustomer) {

        Customer customer = customerRepository.findById(idCustomer).orElse(null);
        customer.getProducts().clear();
        for(Integer i :ids){
            Product p = productRepository.findById(i).orElse(null);
            customer.getProducts().add(p);
            if(!p.getCustomers().contains(customer)){
            p.getCustomers().add(customer);
            }
        }
        customerRepository.save(customer);

        return 1;
    }

}
