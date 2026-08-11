package com.urmish.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberRequestDto {
    @NotBlank(message = "Member name cannot be empty!")
    private String name;

    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Email format is invalid!")
    private String email;
}