package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.Category;
import ma.org.comfybackend.security.Entities.CollectionT;
import ma.org.comfybackend.security.Entities.Product;

import java.io.IOException;
import java.util.List;

public interface ProductsService {

    List<Product> listProducts();

    Product addNewProduct(Product product);

    List<Category>listCategory();

    List<CollectionT> listCollection();

    List<Product> listSelectedProducts(double min, double max);

    List<byte[]> listProductsPhotos(int id) throws IOException;

    byte[] listProductsFirstPhoto(int id) throws IOException;

    List<ProductDTO> listProductsByCatg(int id,double min, double max);

    ProductDTO showOneProduct(int id);

    List<ProductDTO> searchProduct(String name);
}
