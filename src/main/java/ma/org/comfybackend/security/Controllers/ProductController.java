package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.*;
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
import java.util.Optional;

//http://localhost:8084/api/products/show
@RestController
@RequestMapping("api/products")
public class ProductController {
    ProductsService productService;

    public ProductController(ProductsService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/show")
    public List<ProductCDTO> showProducts(){
        return productService.listProducts();
    }

    @GetMapping(path = "/product/{id}")
    public ProductCDTO showOneProduct(@PathVariable int id){
        return productService.showOneProduct(id);
    }
    @PostMapping(path = "/uploadImageProduct/{idProduct}")
    public void uploadImagProduct(@RequestBody Optional<MultipartFile[]> files, @PathVariable int idProduct, @RequestParam(value="dimgs", required = false) List<Integer> dimgs) throws IOException {
        productService.uploadImageProduct(files, idProduct,dimgs);
    }
    @GetMapping(path = "/Find-product/{name}")
    public List<ProductDTO> searchProduct(@PathVariable String name){
        return productService.searchProduct(name);
    }

    @PostMapping(path = "/new")
    public int AddProduct(@RequestBody ProductCDTO product){
        return productService.addNewProduct(product);
    }

    @GetMapping(path = "/categories")
    public List<CategoryDTO> showCategory(){
        return productService.listCategory();
    }

    @GetMapping(path = "/collections")
    public List<CollectionT> showCollection(){
        return productService.listCollection();
    }

    @PostMapping(path = "/newCategory")
    public int AddCategory(@RequestBody CategoryDTO category){
        return productService.addNewCategory(category);
    }

    @DeleteMapping(path = "/dropCategory/{id}")
    public int dropCategory(@PathVariable int id){
        return productService.dropCategory(id);
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

    @DeleteMapping(path = "/removeProduct/{idProduct}")
    public int deleteProduct(@PathVariable int idProduct) {
        return productService.deleteProduct(idProduct);

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
