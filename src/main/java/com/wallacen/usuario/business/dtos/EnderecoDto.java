package com.wallacen.usuario.business.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDto implements Serializable {

    String rua;
    String numero;
    String complemento;
    String cidade;
    String estado;
    String cep;
}