package com.wallacen.usuario.service;

import com.wallacen.usuario.business.converter.UsuarioConverter;
import com.wallacen.usuario.business.dtos.EnderecoDto;
import com.wallacen.usuario.business.dtos.TelefoneDto;
import com.wallacen.usuario.business.dtos.UsuarioDto;
import com.wallacen.usuario.infrastructure.entity.Endereco;
import com.wallacen.usuario.infrastructure.entity.Telefone;
import com.wallacen.usuario.infrastructure.entity.Usuario;
import com.wallacen.usuario.infrastructure.exception.ConflictException;
import com.wallacen.usuario.infrastructure.exception.ResourceNotFoundException;
import com.wallacen.usuario.infrastructure.repository.EnderecoRepository;
import com.wallacen.usuario.infrastructure.repository.TelefoneRepository;
import com.wallacen.usuario.infrastructure.repository.UsuarioRepository;
import com.wallacen.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final TelefoneRepository telefoneRepository;
    private final JwtUtil jwtUtil;

    public UsuarioDto salvarUsuario(UsuarioDto usuarioDto) {
        emailExiste(usuarioDto.getEmail());
        usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDto(usuario);
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificarEmailExistente(email);
            if (existe == true) {
                throw new ConflictException("Email já cadastradp " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificarEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDto buscarUsuarioPorEmail(String email) {

        try {
            Usuario usuarioAtual = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));
            return usuarioConverter.paraUsuarioDto(usuarioAtual);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email nao encontrado " + e.getMessage());
        }
    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDto atualizarUsuario(String token, UsuarioDto usuarioDto) {
        // aqui busquei o usuario atraves do token
        String email = jwtUtil.extractUsername(token.substring(7));

        if (usuarioDto.getSenha() != null) {
            usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        }

        //busco o usuario atual
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email nao localizado"));
        //realizo as altaracoes
        Usuario usuarioNovo = usuarioConverter.updateUsuario(usuarioDto, usuario);

        //salvo o novo usuario
        usuarioRepository.save(usuarioNovo);
        return usuarioConverter.paraUsuarioDto(usuarioNovo);

    }

    public EnderecoDto atualizarEndereco(Long idEndereco, EnderecoDto enderecoDto) {

        Endereco enderecoAtual = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Id na oencontrado"));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDto, enderecoAtual);
        enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDto(endereco);

    }

    public TelefoneDto atualizarTelefone(Long idTelefone, TelefoneDto telefoneDto) {
        Telefone telefoneAtual = telefoneRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("Id na oencontrado"));
        Telefone telefoneNovo = usuarioConverter.updateTelefone(telefoneDto, telefoneAtual);
        telefoneRepository.save(telefoneNovo);
        return usuarioConverter.paraTelefoneDto(telefoneNovo);

    }

    public EnderecoDto cadastraEndereco(String token, EnderecoDto enderecoDto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("usuario nao encontrado"));

        Endereco endereco = usuarioConverter.paraEndereco(enderecoDto, usuario.getId());
        enderecoRepository.save(usuarioConverter.paraEndereco(enderecoDto));
        return enderecoDto;
    }

    public TelefoneDto cadastraTelefone(String token, TelefoneDto telefoneDto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("usuario nao encontrado"));
        usuarioConverter.paraTelefone(telefoneDto, usuario.getId());
        telefoneRepository.save(usuarioConverter.paraTelefone(telefoneDto));
        return telefoneDto;
    }


}
