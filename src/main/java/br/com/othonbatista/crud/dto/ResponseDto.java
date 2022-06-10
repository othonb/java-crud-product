package br.com.othonbatista.crud.dto;

import br.com.othonbatista.crud.model.Product;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ResponseDto {
    List<Product> products;
}
