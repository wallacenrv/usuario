package com.wallacen.usuario.business.dtos;

import com.wallacen.usuario.infrastructure.entity.Endereco;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDto> enderecos;
    private List<TelefoneDto> telefones;

}
