package com.manage.common.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;

@Data
public class PageDto {
    @DecimalMin(value = "0")
    
    private Integer size;
    @DecimalMin(value = "0")
    private Integer current;
}
