package com.urmish.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {

    @NotBlank(message = "Category name cannot be empty!")
    private String name;

}