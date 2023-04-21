package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.CategoryDTO;
import ma.org.comfybackend.security.DTO.ProductCDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.DTO.ReviewDTO;
import ma.org.comfybackend.security.Entities.CollectionT;
import ma.org.comfybackend.security.Entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductsService {

    List<ProductCDTO> listProducts();

    int addNewProduct(ProductCDTO product);

    List<CategoryDTO> listCategory();

    List<CollectionT> listCollection();

    List<ProductDTO> listSelectedProducts(double min, double max);

    List<byte[]> listProductsPhotos(int id) throws IOException;

    byte[] listProductsFirstPhoto(int id) throws IOException;

    List<ProductDTO> listProductsByCatg(int id,double min, double max);

    ProductCDTO showOneProduct(int id);

    List<ProductDTO> searchProduct(String name);

    int createReview(ReviewDTO reviewDTO, int customerId, int productId);

    void uploadImageReview(MultipartFile file, int idReview) throws IOException;

    List<ReviewDTO> listReviews(int idProduct);

    byte[] getRiviewPhoto(int id) throws IOException;

    int deleteReview(int idReview);

    List<ProductDTO> listProducts(List<Integer> ids);

    int addFavorite(List<Integer> ids, int idCustomer);

    int addNewCategory(CategoryDTO category);

    int dropCategory(int id);

    void uploadImageProduct(Optional<MultipartFile[]> file, int idProduct , List<Integer> dimgs) throws IOException;

    int deleteProduct(int iProduct);
}
