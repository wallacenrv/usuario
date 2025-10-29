package com.wallacen.usuario.controller;


import com.wallacen.usuario.controller.dtos.UsuarioDto;
import com.wallacen.usuario.infrastructure.entity.Usuario;
import com.wallacen.usuario.infrastructure.security.JwtUtil;
import com.wallacen.usuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody  Usuario usuario){
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuario));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDto usuarioDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDto.getEmail(), usuarioDto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());

    }

    @GetMapping()
    public ResponseEntity<Usuario> buscaUsuarioPorEmail(@RequestParam ("email")String email){
        Usuario usurio = usuarioService.buscarUsuarioPorEmail(email);
        return ResponseEntity.ok(usurio);
    }

    @DeleteMapping("{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email){
         usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }
}
