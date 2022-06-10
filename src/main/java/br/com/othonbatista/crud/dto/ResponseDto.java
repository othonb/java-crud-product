package br.com.othonbatista.crud.dto;

import br.com.othonbatista.crud.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDto {
    Product productBefore;
    Product productAfter;
    String message;
}
