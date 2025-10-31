package com.wallacen.usuario.business.converter;

import com.wallacen.usuario.business.dtos.EnderecoDto;
import com.wallacen.usuario.business.dtos.TelefoneDto;
import com.wallacen.usuario.business.dtos.UsuarioDto;
import com.wallacen.usuario.infrastructure.entity.Endereco;
import com.wallacen.usuario.infrastructure.entity.Telefone;
import com.wallacen.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDto usuarioDto){
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEndereco(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDto.getTelefones()))
                .build();
    }

    // 1 Forma
    public List<Endereco> paraListaEndereco(List<EnderecoDto> enderecosDto){
        return enderecosDto.stream().map(this::paraEndereco).toList();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDto> telefonesDto){
        return telefonesDto.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDto telefoneDto){
        return Telefone.builder()
                .ddd(telefoneDto.getDdd())
                .numero(telefoneDto.getNumero())
                .build();
    }

    public Endereco paraEndereco(EnderecoDto enderecoDto){
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .complemento(enderecoDto.getComplemento())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .cep(enderecoDto.getCep())
                .build();
    }


    // - ----------------

    public UsuarioDto paraUsuarioDto(Usuario usuario){
        return UsuarioDto.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDto(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDto(usuario.getTelefones()))
                .build();
    }

    // 1 Forma
    public List<EnderecoDto> paraListaEnderecoDto(List<Endereco> enderecos){
        return enderecos.stream().map(this::paraEnderecoDto).toList();
    }

    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> telefones){
        return telefones.stream().map(this::paraTelefoneDto).toList();
    }

    public TelefoneDto paraTelefoneDto(Telefone telefone){
        return TelefoneDto.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public EnderecoDto paraEnderecoDto(Endereco endereco){
        return EnderecoDto.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public Usuario updateUsuario(UsuarioDto usuarioDto, Usuario usuario){
        return Usuario.builder()
                .id(usuario.getId())
                .nome(usuarioDto.getNome() != null ? usuarioDto.getNome() : usuario.getNome())
                .senha(usuarioDto.getSenha() != null ? usuarioDto.getSenha() : usuario.getSenha())
                .email(usuarioDto.getEmail() != null ? usuarioDto.getEmail() : usuario.getEmail())
                .enderecos(paraListaEndereco(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDto.getTelefones()))
                .build();
    }



}

/* 2 Forma
1- Cria lista vazia de Endereco
2- Pega o primeiro elemento de enderecosDtos
3- Converte para Endereco usando paraEndereco()
4- Adiciona na lista enderecos
5- Repete para todos os elementos restantes
6- Retorna a lista completa convertida

public List<Endereco> paraEndereco2(List<EnderecoDto> enderecosDtos){
    List<Endereco> enderecos = new ArrayList<>();
    for (EnderecoDto enderecoDto : enderecosDtos) {
        enderecos.add(paraEndereco(enderecoDto));
    }
    return enderecos;
}


 */