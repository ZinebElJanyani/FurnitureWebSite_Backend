package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.Entities.Product;

import java.util.List;

public interface ProductsService {

    List<Product> listProducts();

    Product addNewProduct(Product product);
}
