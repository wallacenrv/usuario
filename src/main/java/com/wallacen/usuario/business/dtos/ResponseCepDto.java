package com.wallacen.usuario.business.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCepDto {

    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    }
