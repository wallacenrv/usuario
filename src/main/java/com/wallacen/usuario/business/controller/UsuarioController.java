package com.wallacen.usuario.business.controller;


import com.wallacen.usuario.business.dtos.UsuarioDto;
import com.wallacen.usuario.infrastructure.entity.Usuario;
import com.wallacen.usuario.infrastructure.security.JwtUtil;
import com.wallacen.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping()
    public ResponseEntity<UsuarioDto> salvarUsuario(@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDto));
    }


}
