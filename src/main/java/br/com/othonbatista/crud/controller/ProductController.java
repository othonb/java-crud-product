package br.com.othonbatista.crud.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/products"})
public class ProductController {

    // TODO: Estes dois métodos na prática devem ser apenas um, o que recebe o id no path
    @GetMapping
    public String getAll() {

        return "Hello World GET Map";
    }

    // TODO: Apenas este método deve existir. Quando o id é fornecido recupera o produto com o id.
    // TODO: Quando o id não é fornecido, recupera todos os produtos.
    @GetMapping(path = {"/{id}"})
    public String getById(@PathVariable long id) {

        return "Hello World GET By Id: " + id;
    }

    @PostMapping
    public String saveProduct(@RequestBody String msg) {

        return "Hello World POST Save: " + msg;
    }

    @PutMapping(path = {"/{id}"})
    public String updateProductById(@PathVariable long id, @RequestBody String msg) {

        return "Hello World PUT Update, id = " + id + " message: " + msg;
    }

    @DeleteMapping(path = {"/{id}"})
    public String deleteProductById(@PathVariable long id) {

        return "Hello World DELETE Remove, id = " + id;
    }

}
