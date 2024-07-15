package ru.transneft.practice.FunctionalRequirements.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RequirementRegistrationDto {

    @NotNull @NotBlank @NotEmpty
    private String ops;

    @NotNull @NotBlank @NotEmpty
    private String rnu;

    //@NotNull @NotBlank @NotEmpty
    private String ca;

    @NotNull @NotBlank @NotEmpty
    private String reason;

    @NotNull @NotBlank @NotEmpty
    private String author;
}
