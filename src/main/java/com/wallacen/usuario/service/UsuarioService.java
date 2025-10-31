package com.wallacen.usuario.service;

import com.wallacen.usuario.business.converter.UsuarioConverter;
import com.wallacen.usuario.business.dtos.UsuarioDto;
import com.wallacen.usuario.infrastructure.entity.Usuario;
import com.wallacen.usuario.infrastructure.exception.ConflictException;
import com.wallacen.usuario.infrastructure.exception.ResourceNotFoundException;
import com.wallacen.usuario.infrastructure.repository.UsuarioRepository;
import com.wallacen.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDto salvarUsuario(UsuarioDto usuarioDto){
       emailExiste(usuarioDto.getEmail());
       usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDto(usuario);
    }

    public void emailExiste(String email){
        try{
            boolean existe =  verificarEmailExistente(email);
            if (existe == true) {
                throw new ConflictException("Email já cadastradp " + email);
            }
        }catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificarEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDto atualizarUsuario(String token, UsuarioDto usuarioDto){
        // aqui busquei o usuario atraves do token
        String email = jwtUtil.extractUsername(token.substring(7));

        if(usuarioDto.getSenha() != null) {
            usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        }

        //busco o usuario atual
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email nao localizado"));
        //realizo as altaracoes
        Usuario usuarioNovo =  usuarioConverter.updateUsuario(usuarioDto, usuario);

        //salvo o novo usuario
        usuarioRepository.save(usuarioNovo);
        return usuarioConverter.paraUsuarioDto(usuarioNovo);

    }

}
