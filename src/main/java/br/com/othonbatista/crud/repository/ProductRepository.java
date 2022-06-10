package br.com.othonbatista.crud.repository;

import br.com.othonbatista.crud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
