package shoeStore.shoeStore.project.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shoeStore.shoeStore.project.GenericResponse;
import shoeStore.shoeStore.project.dtos.request.ProductRequestDTO;
import shoeStore.shoeStore.project.dtos.response.ProductResponseDTO;
import shoeStore.shoeStore.project.models.Product;
import shoeStore.shoeStore.project.models.ProductSize;
import shoeStore.shoeStore.project.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<ProductResponseDTO> productResponseDTOS = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            BeanUtils.copyProperties(product, productResponseDTO);

            productResponseDTOS.add(productResponseDTO);
        }
        return productResponseDTOS;
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        BeanUtils.copyProperties(product, productResponseDTO);
        return productResponseDTO;
    }


    @Value("${file.product-images-path}")
    private String productImagesPath;

    @Value("${file.preview-images-path}")
    private String previewImagesPath;


    @Override
    public GenericResponse saveProduct(ProductRequestDTO productRequestDTO, MultipartFile productImage, MultipartFile previewImage) {

        List<Product> productList = productRepository.findAll();

        String productImageUrl = saveImage(productImage, productImagesPath);
        String previewImageUrl = saveImage(previewImage, previewImagesPath);

        if (productImage.isEmpty() || previewImage.isEmpty()) {
            return new GenericResponse("Provide ProductImage and ProductPreviewImage", HttpStatus.BAD_REQUEST);
        }

        if (!productList.isEmpty()) {
            for (Product product : productList) {
                if (Objects.equals(product.getProductId(), productRequestDTO.getProductId())) {
                    return new GenericResponse( "Product already exists", HttpStatus.BAD_REQUEST);
                }

                Product product1 = getProduct(productRequestDTO, productImageUrl, previewImageUrl);
                productRepository.save(product1);

                return new GenericResponse("Saved successfully", HttpStatus.OK);
            }
        }

        Product product1 = getProduct(productRequestDTO, productImageUrl, previewImageUrl);
        productRepository.save(product1);

        return new GenericResponse("Saved successfully", HttpStatus.OK);
    }

    @Override
    public GenericResponse editProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            return new GenericResponse("Saved successfully", HttpStatus.OK);
        }
        existingProduct.setName(productRequestDTO.getName());
        existingProduct.setCategory(productRequestDTO.getCategory());
        existingProduct.setPrice(productRequestDTO.getPrice());
        existingProduct.setQuantity(productRequestDTO.getQuantity());
        existingProduct.setColor(productRequestDTO.getColor());

        productRepository.save(existingProduct);

        return new GenericResponse("updated successfully", HttpStatus.OK);
    }

    @Override
    public GenericResponse hideProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return new GenericResponse("Product not found", HttpStatus.NOT_FOUND);
        }

        Product product = productOptional.get();
        product.setHideProduct(!product.isHideProduct());
        productRepository.save(product);

        return new GenericResponse("Done", HttpStatus.OK);
    }


    @Override
    public GenericResponse deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return new GenericResponse("Product not found", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);

        return new GenericResponse("Deleted successfully", HttpStatus.OK);
    }


    private static Product getProduct(ProductRequestDTO productRequestDTO, String productImageUrl, String previewImageUrl) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setCategory(productRequestDTO.getCategory());
        product.setProductId(productRequestDTO.getProductId());
        product.setCategory(productRequestDTO.getCategory());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setImageUrl(productImageUrl);
        product.setImageReviewUrl(previewImageUrl);
        product.setColor(productRequestDTO.getColor());

        List<ProductSize> productSizes = new ArrayList<>();

        List<String> list = productRequestDTO.getSizes();
       for (String string: list){
           ProductSize productSize = new ProductSize();
           productSize.setSize(string);
           productSize.setProduct(product);
           productSizes.add(productSize);
       }
       product.setSizes(productSizes);


        return product;
    }

    private String saveImage(MultipartFile image, String folderPath) {

        String dir = System.getProperty("user.dir") + "/" + folderPath;
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = image.getOriginalFilename();
        String filePath = dir + "/" + fileName;

        try {
            image.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }

        return fileName;
    }
}



