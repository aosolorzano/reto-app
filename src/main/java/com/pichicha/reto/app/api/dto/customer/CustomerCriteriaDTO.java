package com.pichicha.reto.app.api.dto.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerCriteriaDTO {

    private String id;

    private String name;

    private Integer age;
}
