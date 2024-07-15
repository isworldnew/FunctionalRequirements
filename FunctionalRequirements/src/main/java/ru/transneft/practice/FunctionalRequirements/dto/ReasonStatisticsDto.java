package ru.transneft.practice.FunctionalRequirements.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ReasonStatisticsDto {

    private int amount;

    private String reason;
}
