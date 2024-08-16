package shoeStore.shoeStore.project.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String size;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;



}
