package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.CategoryDTO;
import ma.org.comfybackend.security.DTO.ProductCDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.DTO.ReviewDTO;
import ma.org.comfybackend.security.Entities.*;
import ma.org.comfybackend.security.Enumerations.Color;
import ma.org.comfybackend.security.Mappers.ProductMapper;
import ma.org.comfybackend.security.Mappers.ReviewMapper;
import ma.org.comfybackend.security.Repositories.*;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;



@Service("singleTransactionsService")
@Transactional
public class ProductsServiceImpl implements ProductsService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private CollectionTRepository collectionTRepository;
    private ProductMapper productMapper;
    private ReviewMapper reviewMapper;
    private final CustomerRepository customerRepository;
    private ReviewRepository reviewRepository;
    private ItemRepository itemRepository;
    private final RegionRepository regionRepository;
    private final PhotosRepository photosRepository;


    public ProductsServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, CollectionTRepository collectionTRepository,
                               CustomerRepository customerRepository,ReviewRepository reviewRepository,
                               RegionRepository regionRepository,
                               PhotosRepository photosRepository,ItemRepository itemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.collectionTRepository = collectionTRepository;
        this.productMapper = new ProductMapper();
        this.customerRepository = customerRepository;
        this.reviewMapper = new ReviewMapper();
        this.reviewRepository=reviewRepository;
        this.regionRepository = regionRepository;
        this.photosRepository = photosRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ProductCDTO> listProducts() {
        List<Product> products = productRepository.findByDeletedIsFalse();
        List<ProductCDTO> productCDTOS = new ArrayList<>();
        int total;
        for(Product p :products){
            ProductCDTO productCDTO = new ProductCDTO();
            productCDTO.setId(p.getId());
            productCDTO.setCreated_at(p.getCreated_at());
            productCDTO.setColor(p.getColor());
            productCDTO.setDescription(p.getDescription());
            productCDTO.setNom(p.getNom());
            productCDTO.setPrice(p.getPrice());
            productCDTO.setPromotion(p.getPromotion());
            productCDTO.setQteStock(p.getQteStock());
            productCDTO.setSelected(p.isSelected());
            if(p.getReviews().size()!=0){
            total =0;
            for(Review r: p.getReviews()){
                total+=r.getNbre_etoile();
            }
            productCDTO.setStars((total/p.getReviews().size()));
            }else{
                productCDTO.setStars(0);
            }

            if(p.getCategory()!=null) {
                productCDTO.setCategoryId(p.getCategory().getId());
                productCDTO.setCategorytitle(p.getCategory().getTitle());
            }
            productCDTOS.add(productCDTO);
        }
        return productCDTOS;
    }

    @Override
    public int addNewProduct(ProductCDTO productdto) {
        Product product;

        if(productdto.getId()!=0){

            product = productRepository.findById(productdto.getId()).orElse(null);
        }else{
            product = new Product();
        }
        product.setPrice(productdto.getPrice());
        product.setQteStock(productdto.getQteStock());
        product.setPromotion(productdto.getPromotion());
        product.setDescription(productdto.getDescription());
        product.setColor(productdto.getColor());
        product.setCreated_at(productdto.getCreated_at());
        product.setNom(productdto.getNom());
        product.setSelected(productdto.isSelected());
        Category cat = categoryRepository.findById(productdto.getCategoryId());
        product.setCategory(cat);
        Product p = productRepository.save(product);
        return p.getId();
    }

    @Override
    public List<CategoryDTO> listCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category c : categories){
            CategoryDTO cat = new CategoryDTO();
            cat.setTitle(c.getTitle());
            cat.setId(c.getId());
            CollectionT collectionT = this.categoryRepository.getCollectionByCategoryId(c.getId());
            if(collectionT!=null){
            cat.setCollection(collectionT.getTitle());

            categoryDTOS.add(cat);}
        }
        return categoryDTOS;
    }

    @Override
        public List<CollectionT> listCollection() {
        return collectionTRepository.findAll();
    }

    @Override
    public List<ProductDTO> listSelectedProducts(double min , double max,String color) {
        List<Product> products;
        if(color.equals("null")){
            products = productRepository.findBySelectedIsTrueAndDeletedIsFalseAndPriceBetween(min,max);
        }else{
            Color color1 = Color.valueOf(color);
            products = productRepository.findBySelectedIsTrueAndDeletedIsFalseAndPriceBetweenAndColorLike(min,max,color1);

        }
        List<ProductDTO> collect = products.stream().map(p -> this.productMapper.fromProduct(p)).collect(Collectors.toList());

        int total;
        for(int i=0;i<products.size();i++){
            if(products.get(i).getReviews().size()!=0) {
                total = 0;
                for (Review r : products.get(i).getReviews()) {
                    total += r.getNbre_etoile();
                }
                collect.get(i).setStars((total / products.get(i).getReviews().size()));
            }else{
                collect.get(i).setStars(0);
            }
        }
        return collect;
    }

    @Override
    public List<byte[]> listProductsPhotos(int id) throws IOException {
        //on peut untiliser ça: Customer customer = customerRepo.findById(customerID).orElse(null);
        Product p =productRepository.findById(id).get();
        List<byte[]> photos = new ArrayList<>();
        Photos[] imagesN= p.getImages().toArray(new Photos[p.getImages().size()]);

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
    public List<ProductDTO> listProductsByCatg(int id,double min , double max,String color) {
        List<Product> products;
        if(color.equals("null")){
         products = productRepository.findByCategoryIdAndDeletedIsFalseAndPriceBetween(id,min,max);
        }else{
            Color color1 = Color.valueOf(color);
            products = productRepository.findByCategoryIdAndDeletedIsFalseAndPriceBetweenAndColorLike(id,min,max,color1);

        }

       /* System.out.println("koko"+min+":"+max);
        for(Product p : products){
            System.out.println("Nom:"+p.getNom());
            System.out.println("Category:"+p.getCategory().getTitle());
        }*/

        List<ProductDTO> collect = products.stream().map(p -> this.productMapper.fromProduct(p)).collect(Collectors.toList());
        int total;
        for(int i=0;i<products.size();i++){
            if(products.get(i).getReviews().size()!=0) {
                total = 0;
                for (Review r : products.get(i).getReviews()) {
                    total += r.getNbre_etoile();
                }
                collect.get(i).setStars((total / products.get(i).getReviews().size()));
            }else{
                collect.get(i).setStars(0);
            }
        }
        return collect;
    }

    @Override
    public ProductCDTO showOneProduct(int id) {
        Product p = productRepository.findById(id).orElse(null);
        ProductCDTO productCDTO = new ProductCDTO();
        productCDTO.setId(p.getId());
        productCDTO.setCreated_at(p.getCreated_at());
        productCDTO.setColor(p.getColor());
        productCDTO.setDescription(p.getDescription());
        productCDTO.setNom(p.getNom());
        productCDTO.setPrice(p.getPrice());
        productCDTO.setPromotion(p.getPromotion());
        productCDTO.setQteStock(p.getQteStock());
        productCDTO.setSelected(p.isSelected());
        int total =0;
        if(p.getReviews().size()!=0) {
            for (Review r : p.getReviews()) {
                total += r.getNbre_etoile();
            }
            productCDTO.setStars((total / p.getReviews().size()));
        }else{
            productCDTO.setStars(0);
        }

        if(p.getCategory()!=null) {
            productCDTO.setCategoryId(p.getCategory().getId());
            productCDTO.setCategorytitle(p.getCategory().getTitle());
        }



        return productCDTO;
    }

    @Override
    public List<ProductDTO> searchProduct(String name) {
        List<Product> products = productRepository.findByNomContainsAndDeletedIsFalse(name);
        List<ProductDTO> collect = products.stream().map(p -> this.productMapper.fromProduct(p)).collect(Collectors.toList());
        int total;
        for(int i=0;i<products.size();i++){
            if(products.get(i).getReviews().size()!=0) {
                total = 0;
                for (Review r : products.get(i).getReviews()) {
                    total += r.getNbre_etoile();
                }
                collect.get(i).setStars((total / products.get(i).getReviews().size()));
            }else{
                collect.get(i).setStars(0);
            }
        }
        return collect;
    }

    @Override
    public int createReview(ReviewDTO reviewDTO, int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Product product=productRepository.findById(productId).orElse(null);

        Review review = reviewMapper.fromReviewDTO(reviewDTO);
        review.setRecommanded(reviewDTO.getIsRecommanded());

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
            ProductDTO productDTO  = this.productMapper.fromProduct(p);
            int total =0;
            if(p.getReviews().size()!=0){
            for(Review r: p.getReviews()){
                total+=r.getNbre_etoile();
            }
            productDTO.setStars((total/p.getReviews().size()));
            }else {
                productDTO.setStars(0);
            }

            productDTOS.add(productDTO);
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

    @Override
    public int addNewCategory(CategoryDTO category) {
        Category cat ;
        if(category.getId()==0){
            cat =  new Category();
        }else{
            cat = categoryRepository.findById(category.getId());
        }
        cat.setTitle(category.getTitle());
        CollectionT c = collectionTRepository.findByTitle(category.getCollection());

        cat.setCollection(c);

        Category result= this.categoryRepository.save(cat);
        return result.getId();
    }

    @Override
    public int dropCategory(int id) {
        Category category = categoryRepository.findById(id);
        if(category !=null && category.getProducts().size()==0){
            categoryRepository.delete(category);
            return 1;
        }
        return 0;
    }

    @Override
    public void uploadImageProduct(Optional<MultipartFile[]> fileList, int idProduct, List<Integer> dimgslistDeletedIMG) throws IOException {
        Product p = this.productRepository.findById(idProduct).orElse(null);


        if(dimgslistDeletedIMG!=null){
            List<Photos> photosPath = p.getImages().stream().collect(Collectors.toList());

            for(int i=0;i<dimgslistDeletedIMG.size();i++){
                String oldFilePath = System.getProperty("user.home") + "/homeDecor/products/" + photosPath.get(dimgslistDeletedIMG.get(i).intValue()).getImagePath();

                boolean result = Files.deleteIfExists(Paths.get(oldFilePath));
                if(result){

                    try {
                        Photos ph = photosPath.get(dimgslistDeletedIMG.get(i).intValue());
                        Photos photo1 = this.photosRepository.findById(ph.getId());
                        this.photosRepository.delete(photo1);
                    } catch (Exception e) {
                        // Handle the exception here, e.g. log the error message
                        System.err.println("Error deleting photo: " + e.getMessage());
                    }
                }

            }

        }
        if(fileList.isPresent() ) {
            MultipartFile[] files = fileList.orElse(new MultipartFile[0]);

            System.out.println(" i'm here");
            List<Photos> photos = new ArrayList<>();

            for (int i = 0; i < files.length; i++) {

                String contentType = new Tika().detect(files[i].getInputStream());
                String extension = "";

                // Set the extension based on the detected content type
                if (contentType.equals("image/jpeg")) {
                    extension = "jpg";
                } else if (contentType.equals("image/png")) {
                    extension = "png";
                } else {
                    throw new IOException("Unsupported image format");
                }
                // Delete the existing image file

                Photos photos1 = new Photos();
                String uniqueName = System.currentTimeMillis() + "_" + new Random().nextInt(1000) + "." + extension;

                photos1.setImagePath(uniqueName);
                photos1.setProduct(p);
                this.photosRepository.save(photos1);
                photos.add(photos1);
                try {
                    Files.write(Paths.get(System.getProperty("user.home") + "/homeDecor/products/" + photos1.getImagePath()), files[i].getBytes());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            p.setImages(photos);
    }
    }

    @Override
    public int deleteProduct(int idProduct) {
      int a =0;
        Product P = this.productRepository.findById(idProduct).orElse(null);
        if(P.getCommandItems().size()==0 && P.getReviews().size()==0){
            List<Photos> photosPath = P.getImages().stream().collect(Collectors.toList());
            for(Photos photo : photosPath){
                this.photosRepository.delete(photo);
            }
            List<Item> CaddyItems =  P.getItems().stream().collect(Collectors.toList());
            for(Item item : CaddyItems){
                this.itemRepository.delete(item);
            }

            for(Customer c :P.getCustomers()){
                c.getProducts().remove(P);
            }



            this.productRepository.delete(P);
            a = 1;
        }else{
            P.setDeleted(true);
        }
        return a;
    }

    @Override
    public List<ProductDTO> listFavoriteProducts(int id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        Collection<Product> products = customer.getProducts();
        List<ProductDTO> collect = products.stream().map(p -> this.productMapper.fromProduct(p)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<ProductDTO> listProductsOutStock() {
        List<Product>  outOfStocks = this.productRepository.findByQteStockEquals(0);
        List<ProductDTO> collect = outOfStocks.stream().map(p -> this.productMapper.fromProduct(p)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void setSock(int id, int value) {
        Product p = this.productRepository.findById(id).orElse(null);
        p.setQteStock(value);
        productRepository.save(p);
    }

    @Override
    public int getStarsReview() {

        return 0;
    }


}
