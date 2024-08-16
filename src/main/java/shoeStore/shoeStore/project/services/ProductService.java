package shoeStore.shoeStore.project.services;

import org.springframework.web.multipart.MultipartFile;
import shoeStore.shoeStore.project.GenericResponse;
import shoeStore.shoeStore.project.dtos.request.ProductRequestDTO;
import shoeStore.shoeStore.project.dtos.response.ProductResponseDTO;
import shoeStore.shoeStore.project.models.Product;

import java.util.List;


public interface ProductService {

    List<ProductResponseDTO> getAllProducts();

    GenericResponse saveProduct(ProductRequestDTO productRequestDTO, MultipartFile productImage, MultipartFile previewImage);


    GenericResponse deleteProduct(Long id);

    GenericResponse editProduct(Long id, ProductRequestDTO productRequestDTO);

    GenericResponse hideProduct(Long id);

    ProductResponseDTO getProductById(Long productId);

}
