package br.com.othonbatista.crud.controller;

import br.com.othonbatista.crud.dto.ResponseDto;
import br.com.othonbatista.crud.model.Product;
import br.com.othonbatista.crud.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping({"/products"})
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping(path = {"/{id}", ""})
    public ResponseEntity<Object> getById(@PathVariable(required = false) Optional<Long> id) {

        HttpStatus status = HttpStatus.OK;
        Object retorno = null;

        if (id.isPresent()) {

            Optional<Product> product = productRepository.findById(id.get());

            if (product.isPresent()) {

                retorno = product;
                status = HttpStatus.OK;

            } else {

                retorno = "O Produto de id " + id.get() + " não existe!";
                status = HttpStatus.NOT_FOUND;
            }

        } else {

            List<Product> listProducts = productRepository.findAll();

            retorno = listProducts;
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(retorno, status);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {

        productRepository.save(product);

        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Object> updateProductById(@PathVariable long id, @RequestBody Product product) {

        Object retorno = null;
        HttpStatus status = HttpStatus.OK;

        Optional<Product> productSaved = productRepository.findById(id);

        if (productSaved.isPresent()) {

            Product productAfter = productSaved.get();

            Product productBefore = new Product(
                    id,
                    productAfter.getName(),
                    productAfter.getDescription(),
                    productAfter.getPrice(),
                    productAfter.getImageUrl(),
                    productAfter.getQuantity()
            );

            productAfter.setDescription(product.getDescription());
            productAfter.setQuantity(product.getQuantity());
            productAfter.setPrice(product.getPrice());
            productAfter.setImageUrl(product.getImageUrl());
            productAfter.setName(product.getName());

            productRepository.save(productAfter);

            ResponseDto responseDto = new ResponseDto(
                    productBefore,
                    productAfter,
                    "O produto com id " + id + " foi alterado com sucesso!!!"
            );

            retorno = responseDto;
            status = HttpStatus.CREATED;

        } else {

            retorno = "O produto com id " + id + " não existe!";
            status = HttpStatus.NOT_FOUND;

        }

        return new ResponseEntity<>(retorno, status);
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Object> deleteProductById(@PathVariable long id) {

        Object retorno;
        HttpStatus status;

        Optional<Product> productSaved = productRepository.findById(id);

        if (productSaved.isPresent()) {

            retorno = productSaved;
            status = HttpStatus.OK;

            productRepository.deleteById(id);

        } else {

            retorno = "O produto com id " + id + " não existe!";
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(retorno, status);
    }

}
