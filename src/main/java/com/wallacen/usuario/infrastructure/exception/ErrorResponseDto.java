package com.wallacen.usuario.infrastructure.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ErrorResponseDto {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
    private String path;
    private String error;
}
