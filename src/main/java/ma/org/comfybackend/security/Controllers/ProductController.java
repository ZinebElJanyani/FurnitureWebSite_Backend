package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.CollectionT;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Repositories.CollectionTRepository;
import ma.org.comfybackend.security.Services.ProductsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(path = "/selected_P")
    public List<Product> showSelectedProducts(){
        return productService.listSelectedProducts();
    }
    @GetMapping(path = "/products_catg/{id}")
    public List<ProductDTO> showProductsByCatg(@PathVariable int id){
        return productService.listProductsByCatg(id);
    }

    @GetMapping(path = "/productP/{id}")
    public List<byte[]> showProductsPhotos(@PathVariable int id) throws IOException {
        return productService.listProductsPhotos(id);
    }

    @GetMapping(path = "/productFirst_P/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showProductsFirstPhoto(@PathVariable int id) throws IOException {
        return productService.listProductsFirstPhoto(id);
    }
}
