package ru.transneft.practice.FunctionalRequirements.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.transneft.practice.FunctionalRequirements.dto.ReasonStatisticsDto;
import ru.transneft.practice.FunctionalRequirements.dto.RequirementRegistrationDto;
import ru.transneft.practice.FunctionalRequirements.entities.Requirement;
import ru.transneft.practice.FunctionalRequirements.mappers.RequirementMappers;
import ru.transneft.practice.FunctionalRequirements.repositories.RequirementsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequirementsService {

    private final RequirementsRepository requirementsRepository;

    private final RequirementMappers requirementMappers;

    @Autowired
    public RequirementsService(
            RequirementsRepository requirementsRepository,
            RequirementMappers requirementMappers
    ) {
        this.requirementsRepository = requirementsRepository;
        this.requirementMappers = requirementMappers;
    }

    //основные (базовые) действия:
    public ResponseEntity<Requirement> saveNewFunctionalRequirement(RequirementRegistrationDto dto) {
        Requirement savedRequirement = requirementsRepository.save(
                requirementMappers.requirementRegistrationDtoToRequirementDocument(dto)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequirement);
    }

    public ResponseEntity<List<Requirement>> getAllRequirements() {
        return ResponseEntity.ok(requirementsRepository.findAll());
    }

    public ResponseEntity<Requirement> updateExistingFunctionalRequirement(Requirement requirement) {
        Requirement requirementToUpdate = this.getFunctionalRequirementById(requirement.getId()).getBody();
//        requirementToUpdate.setId();
//        requirementToUpdate.setCreationDate();
        requirementToUpdate.setOps(requirement.getOps());
        requirementToUpdate.setRnu(requirement.getRnu());
        requirementToUpdate.setCa(requirement.getCa());
        requirementToUpdate.setReason(requirement.getReason());
        requirementToUpdate.setRealizationDate(requirement.getRealizationDate());
//        requirementToUpdate.setAuthor();
        requirementsRepository.save(requirementToUpdate);
        return ResponseEntity.ok(requirementToUpdate);
    }

    public ResponseEntity<Void> deleteFunctionalRequirementById(String id) {
        requirementsRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }
    //=========================================================================================================================


    //поиск по текстосодержащим полям:
    public ResponseEntity<Requirement> getFunctionalRequirementById(String id) {
        return ResponseEntity.ok(
                requirementsRepository.findById(id).orElse(null)
        );
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByOps(String ops) {
        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getOps().equals(ops))
                        .collect(Collectors.toList())
        );
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByRnu(String rnu) {
        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getRnu().equals(rnu))
                        .collect(Collectors.toList())
        );
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByCa(String ca) {
        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getCa().equals(ca))
                        .collect(Collectors.toList())
        );
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByAuthor(String author) {
        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getAuthor().contains(author))
                        .collect(Collectors.toList())
        );
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByReason(String reason) {
        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getReason().contains(reason))
                        .collect(Collectors.toList())
        );
    }
    //=========================================================================================================================


    //поиск по датам:
    public ResponseEntity<List<Requirement>> getAllRequirementsByCreationYear(Integer year) {

        List<Requirement> requirements = this.getAllRequirements().getBody();

        List<Requirement> filteredRequirements = requirements.stream()
                .filter(requirement -> requirement
                        .getCreationDate()
                        .toString()
                        .startsWith(
                                year.toString()
                        ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredRequirements);
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByCreationYearAndMonth(Integer month, Integer year) {

        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getCreationDate().getYear() == year)
                        .filter(requirement -> requirement.getCreationDate().getMonth().getValue() == month)
                        .collect(Collectors.toList())
        );

    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByCreationDate(Integer day, Integer month, Integer year) {

        String date = year + "-";

        if (month / 10 == 0) date += "0" + month + "-";
        else date += month + "-";

        if (day / 10 == 0) date += "0" + day;
        else date += day;

        final String finalDate = date;

        return ResponseEntity.ok(
                this.getAllRequirements().getBody().stream()
                        .filter(
                                requirement -> requirement.getCreationDate()
                                        .toString()
                                        .startsWith(finalDate)
                        )
                        .collect(Collectors.toList())
        );
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByRealizationYear(Integer year) {
        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getRealizationDate().getYear() == year)
                        .collect(Collectors.toList())
        );
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByRealizationYearAndMonth(Integer month, Integer year) {
        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getRealizationDate().getYear() == year)
                        .filter(requirement -> requirement.getRealizationDate().getMonth().getValue() == month)
                        .collect(Collectors.toList())
        );
    }

    public ResponseEntity<List<Requirement>> getAllRequirementsByRealizationDate(Integer day, Integer month, Integer year) {

        List<Requirement> requirements = this.getAllRequirements().getBody();

        return ResponseEntity.ok(
                requirements.stream()
                        .filter(requirement -> requirement.getRealizationDate().getYear() == year)
                        .filter(requirement -> requirement.getRealizationDate().getMonth().getValue() == month)
                        .filter(requirement -> requirement.getRealizationDate().getDayOfMonth() == day)
                        .collect(Collectors.toList())
        );
    }
    //=========================================================================================================================

    //статистика:
    public List<ReasonStatisticsDto> getReasonStatistics() {
        return requirementsRepository.getReasonStatistics();
    }

    //в отдельный контроллер вынести всё то, что касается статистики: сортировки, группировки

}
