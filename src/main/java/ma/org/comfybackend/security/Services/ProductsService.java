package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.CollectionT;
import ma.org.comfybackend.security.Entities.Product;

import java.util.List;

public interface ProductsService {

    List<Product> listProducts();

    Product addNewProduct(Product product);

    List<Category>listCategory();

    List<CollectionT> listCollection();

    List<Product> listSelectedProducts();
}
