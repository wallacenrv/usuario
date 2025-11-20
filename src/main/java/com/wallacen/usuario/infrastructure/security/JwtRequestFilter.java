package com.wallacen.usuario.infrastructure.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wallacen.usuario.infrastructure.exception.ErrorResponseDto;
import com.wallacen.usuario.infrastructure.exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

// Define a classe JwtRequestFilter, que estende OncePerRequestFilter
public class JwtRequestFilter extends OncePerRequestFilter {

    // Define propriedades para armazenar instâncias de JwtUtil e UserDetailsService
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Construtor que inicializa as propriedades com instâncias fornecidas
    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Método chamado uma vez por requisição para processar o filtro
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtém o valor do header "Authorization" da requisição
        final String authorizationHeader = request.getHeader("Authorization");

        try {
            // Verifica se o cabeçalho existe e começa com "Bearer "
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // Extrai o token JWT do cabeçalho
                final String token = authorizationHeader.substring(7);
                // Extrai o nome de usuário do token JWT
                final String username = jwtUtil.extractUsername(token);

                // Se o nome de usuário não for nulo e o usuário não estiver autenticado ainda
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Carrega os detalhes do usuário a partir do nome de usuário
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // Valida o token JWT
                    if (jwtUtil.validateToken(token, username)) {
                        // Cria um objeto de autenticação com as informações do usuário
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        // Define a autenticação no contexto de segurança
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (ExpiredJwtException ex) { //retorna o erro 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json"); // para que ele entenda que nosso retorno é um json
            response.getWriter().write(buildError(HttpStatus.UNAUTHORIZED.value(),
                    "Token expirado",
                    request.getRequestURI(),
                    ex.getMessage()));
             }

        // Continua a cadeia de filtros, permitindo que a requisição prossiga
        chain.doFilter(request, response);
    }

    private String buildError(int status, String message, String path,String error) throws JsonProcessingException {
      ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .status(status)
                .path(path)
                .error(error)
                .build();


      //converter o Ojbeto em um string
      ObjectMapper objectMapper = new ObjectMapper();

      //colocamos isso pq o ObjectModel nao sabe desesserializar o LocaldateTime
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
     return objectMapper.writeValueAsString(errorResponseDto);
    }
}
