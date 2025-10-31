package com.wallacen.usuario.business.controller;


import com.wallacen.usuario.business.dtos.EnderecoDto;
import com.wallacen.usuario.business.dtos.TelefoneDto;
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
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostMapping()
    public ResponseEntity<UsuarioDto> salvarUsuario(@RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDto));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDto usuarioDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDto.getEmail(), usuarioDto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());

    }

    @GetMapping()
    public ResponseEntity<UsuarioDto> buscaUsuarioPorEmail(@RequestParam ("email")String email){
        UsuarioDto usuario = usuarioService.buscarUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    // alterar dados de usuario
    // Quando eu quero pegar o usuario pelo token, tenho que colocar esse RequestHeader
    @PutMapping()
    public ResponseEntity<UsuarioDto> atualizarDadosUsuario(@RequestHeader("Authorization") String token, @RequestBody UsuarioDto usuarioDto){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(token, usuarioDto));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDto> atualizarEndereco(@RequestParam("id") Long idEndereco,
                                                         @RequestBody EnderecoDto enderecoDto){
        return ResponseEntity.ok(usuarioService.atualizarEndereco(idEndereco, enderecoDto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDto> atualizarTelefone(@RequestParam ("id") Long idTelefone,
                                                         @RequestBody TelefoneDto telefoneDto){
        return ResponseEntity.ok(usuarioService.atualizarTelefone(idTelefone, telefoneDto));
    }

}
