package com.wallacen.usuario.service;

import com.wallacen.usuario.business.dtos.ResponseCepDto;
import com.wallacen.usuario.infrastructure.client.ViaCepClient;
import com.wallacen.usuario.infrastructure.exception.IllegalArgumentsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

private final ViaCepClient viaCepClient;

    public ResponseCepDto buscarCep(String cep) {
        try {
            return viaCepClient.buscaDadosEndereco(processarCep(cep));
        } catch (IllegalArgumentsException Il) {
            throw new IllegalArgumentsException("Cep com formatacao errada");
        }
    }
    
    private String processarCep(String cep) {
        String cpfFormatado = cep.replace(" ", "").replace("-", "").trim();

        if (!cpfFormatado.matches("\\d+") || !Objects.equals(cpfFormatado, 8)) {
            throw  new IllegalArgumentsException("o cep contem caracteres ivalidos ");
        }
        return cpfFormatado;

    }
}
