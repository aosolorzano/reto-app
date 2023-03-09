package com.pichicha.reto.app.api.dto.customer;

import com.pichicha.reto.app.api.utils.enums.EnumGenre;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCriteriaDTO {

    private String id;

    private String nombre;

    private EnumGenre genero;

    private Integer edad;

    private String direccion;

    private String telefono;
}
