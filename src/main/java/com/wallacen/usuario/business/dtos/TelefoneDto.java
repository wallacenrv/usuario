package com.wallacen.usuario.business.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDto implements Serializable {

    String numero;
    String ddd;
}