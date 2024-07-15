package ru.transneft.practice.FunctionalRequirements.mappers;

import org.springframework.stereotype.Component;
import ru.transneft.practice.FunctionalRequirements.dto.RequirementRegistrationDto;
import ru.transneft.practice.FunctionalRequirements.entities.Requirement;

import java.time.LocalDate;

@Component
public class RequirementMappers {

    public Requirement requirementRegistrationDtoToRequirementDocument(RequirementRegistrationDto dto) {
        Requirement requirement = new Requirement();
        requirement.setCreationDate(LocalDate.now());
        requirement.setOps(dto.getOps());
        requirement.setRnu(dto.getRnu());
        requirement.setCa(dto.getCa());
        requirement.setReason(dto.getReason());
        requirement.setRealizationDate(null);
        requirement.setAuthor(dto.getAuthor());
        return requirement;
    }

}
