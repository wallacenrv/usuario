package com.wallacen.usuario.business.controller;


import com.wallacen.usuario.business.dtos.EnderecoDto;
import com.wallacen.usuario.business.dtos.ResponseCepDto;
import com.wallacen.usuario.business.dtos.TelefoneDto;
import com.wallacen.usuario.business.dtos.UsuarioDto;
import com.wallacen.usuario.infrastructure.entity.Usuario;
import com.wallacen.usuario.infrastructure.security.JwtUtil;
import com.wallacen.usuario.infrastructure.security.SecurityConfig;
import com.wallacen.usuario.service.UsuarioService;
import com.wallacen.usuario.service.ViaCepService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name="Usuario", description = "cadastro e login e usuarios")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEMA)

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ViaCepService viaCepService;


    @GetMapping("/endereco/{cep}")
    public ResponseEntity<ResponseCepDto> buscarDadosCep(@PathVariable("cep") String cep){
        return ResponseEntity.ok(viaCepService.buscarCep(cep));
    }

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
    public ResponseEntity<UsuarioDto> buscaUsuarioPorEmail(@RequestParam ("email")String email,@RequestHeader("Authorization") String token){
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

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDto> cadastraTelefone(@RequestHeader("Authorization") String token,
                                                         @RequestBody TelefoneDto telefoneDto){
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, telefoneDto));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDto> cadastraEndereco(@RequestHeader("Authorization") String token,
                                                         @RequestBody EnderecoDto enderecoDto){
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, enderecoDto));
    }



}
