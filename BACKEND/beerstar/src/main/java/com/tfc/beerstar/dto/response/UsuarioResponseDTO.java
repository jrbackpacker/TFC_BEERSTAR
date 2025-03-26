package com.tfc.beerstar.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String email;
    private String rol;
    private String tipoUsuario;

}
