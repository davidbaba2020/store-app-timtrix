package shoeStore.shoeStore.project.dtos.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private double productId;
    private String name;
    private String category;
    private int price;
    private int quantity;
    private String imageUrl;
    private String imageReviewUrl;
    private String color;
    private List<String> sizes;


}
