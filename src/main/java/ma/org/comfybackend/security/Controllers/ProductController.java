package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.DTO.ReviewDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.CollectionT;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Repositories.CollectionTRepository;
import ma.org.comfybackend.security.Services.ProductsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;
//http://localhost:8084/api/products/show
@RestController
@RequestMapping("api/products")
public class ProductController {
    ProductsService productService;

    public ProductController(ProductsService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/show")
    public List<Product> showProducts(){
        return productService.listProducts();
    }

    @GetMapping(path = "/product/{id}")
    public ProductDTO showOneProduct(@PathVariable int id){
        return productService.showOneProduct(id);
    }

    @GetMapping(path = "/Find-product/{name}")
    public List<ProductDTO> searchProduct(@PathVariable String name){
        return productService.searchProduct(name);
    }

    @PostMapping(path = "/new")
    public Product AddProduct(@RequestBody Product product){
        return productService.addNewProduct(product);
    }

    @GetMapping(path = "/categories")
    public List<Category> showCategory(){
        return productService.listCategory();
    }

    @GetMapping(path = "/collections")
    public List<CollectionT> showCollection(){
        return productService.listCollection();
    }


    @GetMapping(path = "/selected_P/{min}/{max}")
    public List<ProductDTO> showSelectedProducts(@PathVariable double min, @PathVariable double max){
        return productService.listSelectedProducts(min,max);
    }
    @GetMapping(path = "/products_catg/{id}/{min}/{max}")
    public List<ProductDTO> showProductsByCatg(@PathVariable int id,@PathVariable double min, @PathVariable double max){
        return productService.listProductsByCatg(id,min,max);
    }

    @GetMapping(path = "/productP/{id}")
    public List<byte[]> showProductsPhotos(@PathVariable int id) throws IOException {
        return productService.listProductsPhotos(id);
    }

    @GetMapping(path = "/productFirst_P/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showProductsFirstPhoto(@PathVariable int id) throws IOException {
        return productService.listProductsFirstPhoto(id);
    }

    @PostMapping(path = "/createReview")
    public int createReview(@RequestBody ReviewDTO reviewDTO, @RequestParam("costomerId") int customer_id, @RequestParam("productId") int product_id){
        return productService.createReview(reviewDTO,customer_id,product_id);
    }

    @PostMapping(path = "/uploadImageReview/{idReview}")
    public void uploadImageReview(MultipartFile file, @PathVariable int idReview) throws IOException {
        productService.uploadImageReview(file, idReview);
    }

    @GetMapping(path = "/reviews/{idProduct}")
    public List<ReviewDTO> showSelectedProducts(@PathVariable int idProduct){
        return productService.listReviews(idProduct);
    }
    @GetMapping(path = "/ReviewImage/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showReviewImg(@PathVariable int id) throws IOException {
        return productService.getRiviewPhoto(id);
    }
    @DeleteMapping(path = "/removeReview/{idReview}")
    public int deleteReview(@PathVariable int idReview) {
       return productService.deleteReview(idReview);

    }

    @GetMapping(path = "/products")
    public List<ProductDTO> showProducts(@RequestParam List<Integer> ids){
        return productService.listProducts(ids);
    }

    @PostMapping(path = "/addFavorite/{id_customer}")
    public int addFavorite(@RequestParam List<Integer> ids,@PathVariable int id_customer){
        return productService.addFavorite(ids,id_customer);
    }
}
