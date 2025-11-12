package com.wallacen.usuario.business.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDtoRequest {

    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDto> enderecos;
    private List<TelefoneDto> telefones;

}
