package shoeStore.shoeStore.project.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shoeStore.shoeStore.project.GenericResponse;
import shoeStore.shoeStore.project.dtos.request.ProductRequestDTO;
import shoeStore.shoeStore.project.dtos.response.ProductResponseDTO;
import shoeStore.shoeStore.project.models.Product;
import shoeStore.shoeStore.project.services.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> productResponseDTOS = productService.getAllProducts();
        return new ResponseEntity<>(productResponseDTOS, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
        ProductResponseDTO productResponseDTOS = productService.getProductById(productId);
        return new ResponseEntity<>(productResponseDTOS, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addProduct")
    public ResponseEntity<GenericResponse> addProduct(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("productImage") MultipartFile productImage,
            @RequestParam("previewImage") MultipartFile previewImage,
            @RequestParam("productId") String productId,
            @RequestParam("price") Integer price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("color") String color,
            @RequestParam("sizes") List<String> sizes
    ) {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setName(name);
        productRequestDTO.setCategory(category);
        productRequestDTO.setProductId(productId);
        productRequestDTO.setPrice(price);
        productRequestDTO.setQuantity(quantity);
        productRequestDTO.setColor(color);
        productRequestDTO.setSizes(sizes);

        GenericResponse genericResponse = productService.saveProduct(productRequestDTO, productImage, previewImage);
        return new ResponseEntity<>(genericResponse, genericResponse.getHttpStatus());
    }



    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/editProduct/{id}")
    public ResponseEntity<GenericResponse> editProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO
    ) {
        GenericResponse genericResponse = productService.editProduct(id, productRequestDTO);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/hideProduct/{id}")
    public ResponseEntity<GenericResponse> hideProduct(@PathVariable Long id
    ) {


        GenericResponse genericResponse = productService.hideProduct(id);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<GenericResponse> deleteProduct(@PathVariable Long id) {
        GenericResponse genericResponse = productService.deleteProduct(id);

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }


}
