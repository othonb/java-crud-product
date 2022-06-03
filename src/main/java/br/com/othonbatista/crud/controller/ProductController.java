package br.com.othonbatista.crud.controller;

import br.com.othonbatista.crud.model.Product;
import br.com.othonbatista.crud.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/products"})
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // TODO: Estes dois métodos na prática devem ser apenas um, o que recebe o id no path
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {

        List<Product> productsList = this.productRepository.findAll();

        return new ResponseEntity<List<Product>>(productsList, HttpStatus.OK);
    }

    // TODO: Apenas este método deve existir. Quando o id é fornecido recupera o produto com o id.
    // TODO: Quando o id não é fornecido, recupera todos os produtos.
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Optional<Product>> getById(@PathVariable long id) {

        Optional<Product> product = this.productRepository.findById(id);

        return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {

        this.productRepository.save(product);

        return new ResponseEntity<String>("Product with id " + product.getId() + " saved!!!", HttpStatus.CREATED);
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<String> updateProductById(@PathVariable long id, @RequestBody Product product) {

        String message = null;
        HttpStatus status = HttpStatus.OK;

        Optional<Product> productSaved = this.productRepository.findById(id);

        if (productSaved.isPresent()) {

            Product product1 = productSaved.get();

            product1.setDescription(product.getDescription());
            product1.setName(product.getName());
            product1.setImageUrl(product.getImageUrl());
            product1.setPrice(product.getPrice());
            product1.setQuantity(product.getQuantity());

            productRepository.save(product1);

            message = "Product with id " + id + " updated";
            status = HttpStatus.CREATED;

        } else {

            message = "There is no product with id " + id;
            status = HttpStatus.NOT_FOUND;

        }

        return new ResponseEntity<String>(message, status);

    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<String> deleteProductById(@PathVariable long id) {

        productRepository.deleteById(id);

        return new ResponseEntity<String>("Product with id " + id + " deleted!!!", HttpStatus.CREATED);
    }

}
