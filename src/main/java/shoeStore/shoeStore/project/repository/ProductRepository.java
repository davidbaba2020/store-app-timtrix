package shoeStore.shoeStore.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoeStore.shoeStore.project.models.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
}
