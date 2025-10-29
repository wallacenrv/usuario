package com.wallacen.usuario.service;

import com.wallacen.usuario.business.converter.UsuarioConverter;
import com.wallacen.usuario.business.dtos.UsuarioDto;
import com.wallacen.usuario.infrastructure.entity.Usuario;
import com.wallacen.usuario.infrastructure.exception.ConflictException;
import com.wallacen.usuario.infrastructure.exception.ResourceNotFoundException;
import com.wallacen.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDto salvarUsuario(UsuarioDto usuarioDto){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDto(usuario);
    }

}
