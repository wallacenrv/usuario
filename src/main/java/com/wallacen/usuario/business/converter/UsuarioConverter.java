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

    // Converte UsuarioDto para Usuario
    public Usuario paraUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(usuarioDto.getEnderecos() != null ? paraListaEndereco(usuarioDto.getEnderecos()) : new ArrayList<>())  // Garantir lista vazia
                .telefones(usuarioDto.getTelefones() != null ? paraListaTelefone(usuarioDto.getTelefones()) : new ArrayList<>())  // Garantir lista vazia
                .build();
    }

    // Converte lista de EnderecoDto para lista de Enderecos
    public List<Endereco> paraListaEndereco(List<EnderecoDto> enderecosDto) {
        if (enderecosDto != null) {
            return enderecosDto.stream().map(this::paraEndereco).toList();
        }
        return new ArrayList<>();  // Garantir lista vazia caso seja null
    }

    // Converte lista de TelefoneDto para lista de Telefones
    public List<Telefone> paraListaTelefone(List<TelefoneDto> telefonesDto) {
        if (telefonesDto != null) {
            return telefonesDto.stream().map(this::paraTelefone).toList();
        }
        return new ArrayList<>();  // Garantir lista vazia caso seja null
    }

    // Converte TelefoneDto para Telefone
    public Telefone paraTelefone(TelefoneDto telefoneDto) {
        return Telefone.builder()
                .ddd(telefoneDto.getDdd())
                .numero(telefoneDto.getNumero())
                .build();
    }

    // Converte EnderecoDto para Endereco
    public Endereco paraEndereco(EnderecoDto enderecoDto) {
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .complemento(enderecoDto.getComplemento())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .cep(enderecoDto.getCep())
                .build();
    }

    // Converte Usuario para UsuarioDto
    public UsuarioDto paraUsuarioDto(Usuario usuario) {
        return UsuarioDto.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(usuario.getEnderecos() != null ? paraListaEnderecoDto(usuario.getEnderecos()) : new ArrayList<>())  // Garantir lista vazia
                .telefones(usuario.getTelefones() != null ? paraListaTelefoneDto(usuario.getTelefones()) : new ArrayList<>())  // Garantir lista vazia
                .build();
    }

    // Converte lista de Enderecos para lista de EnderecoDto
    public List<EnderecoDto> paraListaEnderecoDto(List<Endereco> enderecos) {
        if (enderecos != null) {
            return enderecos.stream().map(this::paraEnderecoDto).toList();
        }
        return new ArrayList<>();  // Garantir lista vazia caso seja null
    }

    // Converte lista de Telefones para lista de TelefoneDto
    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> telefones) {
        if (telefones != null) {
            return telefones.stream().map(this::paraTelefoneDto).toList();
        }
        return new ArrayList<>();  // Garantir lista vazia caso seja null
    }

    // Converte Telefone para TelefoneDto
    public TelefoneDto paraTelefoneDto(Telefone telefone) {
        return TelefoneDto.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    // Converte Endereco para EnderecoDto
    public EnderecoDto paraEnderecoDto(Endereco endereco) {
        return EnderecoDto.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    // Atualiza dados do Usuario
    public Usuario updateUsuario(UsuarioDto usuarioDto, Usuario usuario) {
        return Usuario.builder()
                .id(usuario.getId())
                .nome(usuarioDto.getNome() != null ? usuarioDto.getNome() : usuario.getNome())
                .senha(usuarioDto.getSenha() != null ? usuarioDto.getSenha() : usuario.getSenha())
                .email(usuarioDto.getEmail() != null ? usuarioDto.getEmail() : usuario.getEmail())
                .enderecos(paraListaEndereco(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDto.getTelefones()))
                .build();
    }

    // Atualiza dados do Endereco
    public Endereco updateEndereco(EnderecoDto enderecoDto, Endereco endereco) {
        return Endereco.builder()
                .id(endereco.getId())
                .complemento(enderecoDto.getComplemento() != null ? enderecoDto.getComplemento() : endereco.getComplemento())
                .cidade(enderecoDto.getCidade() != null ? enderecoDto.getCidade() : endereco.getCidade())
                .estado(enderecoDto.getEstado() != null ? enderecoDto.getEstado() : endereco.getEstado())
                .rua(enderecoDto.getRua() != null ? enderecoDto.getRua() : endereco.getRua())
                .numero(enderecoDto.getNumero() != null ? enderecoDto.getNumero() : endereco.getNumero())
                .cep(enderecoDto.getCep() != null ? enderecoDto.getCep() : endereco.getCep())
                .build();
    }

    // Atualiza dados do Telefone
    public Telefone updateTelefone(TelefoneDto telefoneDto, Telefone telefone) {
        return Telefone.builder()
                .id(telefone.getId())
                .ddd(telefoneDto.getDdd() != null ? telefoneDto.getDdd() : telefone.getDdd())
                .numero(telefoneDto.getNumero() != null ? telefoneDto.getNumero() : telefone.getNumero())
                .build();
    }

    // Converte EnderecoDto para Endereco com id de usuario
    public Endereco paraEndereco(EnderecoDto enderecoDto, Long idUsuario) {
        return Endereco.builder()
                .usuario_id(idUsuario)
                .complemento(enderecoDto.getComplemento())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .rua(enderecoDto.getRua())
                .cep(enderecoDto.getCep())
                .numero(enderecoDto.getNumero())
                .build();
    }

    // Converte TelefoneDto para Telefone com id de usuario
    public Telefone paraTelefone(TelefoneDto telefoneDto, Long idUsuario) {
        return Telefone.builder()
                .usuario_id(idUsuario)
                .ddd(telefoneDto.getDdd())
                .numero(telefoneDto.getNumero())
                .build();
    }
}


// {
//         "email": "wallacen@email.com",
//         "senha": "123456"
//         }
//
//{
//        "nome": "Administrador",
//        "email": "admin@admin.com",
//        "senha": "1234"
//        }