package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.Entities.AppUser;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Services.ProductsService;
import org.springframework.web.bind.annotation.*;

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
}
