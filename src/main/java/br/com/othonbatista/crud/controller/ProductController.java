package br.com.othonbatista.crud.controller;

import br.com.othonbatista.crud.dto.ResponseDto;
import br.com.othonbatista.crud.model.Product;
import br.com.othonbatista.crud.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// TODO: Não deixar que ocorra CORS nas solicitações (permita todas as origens)

// TODO: tratar as exeções que podem ocorrer com bancos de dados

// TODO: validar os parâmetros quando necessário
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

        List<Product> listProducts = productRepository.findAll();

        // TODO: Retornar os produtos no formato requerido pelo frontend angular
//        ResponseDto responseDto = new ResponseDto(listProducts);

        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }

    // TODO: Apenas este método deve existir. Quando o id é fornecido recupera o produto com o id.
    // TODO: Quando o id não é fornecido, recupera todos os produtos.
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Optional<Product>> getById(@PathVariable long id) {

        HttpStatus status = HttpStatus.OK;

        Optional<Product> product = productRepository.findById(id);

        // TODO: Como retornar uma mensagem se não houver o produto consultado
        if (!product.isPresent()) {
            // ?????
        }

        return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {

        productRepository.save(product);

        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<String> updateProductById(@PathVariable long id, @RequestBody Product product) {

        String message = null;
        HttpStatus status = HttpStatus.OK;

        Optional<Product> productSaved = productRepository.findById(id);

        if (productSaved.isPresent()) {

            Product productToUpdate = productSaved.get();

            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setQuantity(product.getQuantity());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setImageUrl(product.getImageUrl());
            productToUpdate.setName(product.getName());

            productRepository.save(productToUpdate);

            message = "O produto com id " + id + " foi alterado com sucesso!!!";
            status = HttpStatus.CREATED;

        } else {

            message = "O produto com id " + id + " não existe!";
            status = HttpStatus.NOT_FOUND;

        }

        return new ResponseEntity<String>(message, status);
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Optional<Product>> deleteProductById(@PathVariable long id) {

        Optional<Product> productSaved = productRepository.findById(id);

        productRepository.deleteById(id);

        return new ResponseEntity<Optional<Product>>(productSaved, HttpStatus.OK);
    }

}
