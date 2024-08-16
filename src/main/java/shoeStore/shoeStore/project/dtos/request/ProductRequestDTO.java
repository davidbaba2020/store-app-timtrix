package shoeStore.shoeStore.project.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductRequestDTO {

    @NotNull(message = "Product name cannot be empty")
    private String name;

    private String category;

    private String productId; // Change type to String if it's an identifier

    @NotNull(message = "Price must be provided")
    private Integer price;

    @NotNull(message = "Quantity must be provided")
    private Integer quantity;

    private String color;

    @NotEmpty(message = "Sizes cannot be empty")
    private List<String> sizes;
}
