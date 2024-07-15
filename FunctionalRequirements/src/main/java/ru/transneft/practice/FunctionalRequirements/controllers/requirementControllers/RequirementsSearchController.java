package ru.transneft.practice.FunctionalRequirements.controllers.requirementControllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.transneft.practice.FunctionalRequirements.dto.ReasonStatisticsDto;
import ru.transneft.practice.FunctionalRequirements.entities.Requirement;
import ru.transneft.practice.FunctionalRequirements.services.RequirementsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/requirementsSearch")
@CrossOrigin(origins = "http://localhost:4004")
public class RequirementsSearchController {

    private final RequirementsService requirementsService;

    @Autowired
    public RequirementsSearchController(RequirementsService requirementsService) {
        this.requirementsService = requirementsService;
    }
    //=============================================================================================================================

    //статистика:
    @GetMapping("/getReasonStatistics")
    public List<ReasonStatisticsDto> getReasonStatistics() {
        return requirementsService.getReasonStatistics();
    }

    //поиск по текстосодержащим полям:
    @GetMapping("/getFunctionalRequirementById/{id}")
    public ResponseEntity<Requirement> getFunctionalRequirementById(@NotNull @NotBlank @NotEmpty @PathVariable String id) {
        return requirementsService.getFunctionalRequirementById(id);
    }

    @GetMapping("/getAllRequirementsByOps/{ops}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByOps(@NotNull @NotBlank @NotEmpty @PathVariable String ops) {
        return requirementsService.getAllRequirementsByOps(ops);
    }

    @GetMapping("/getAllRequirementsByRnu/{rnu}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByRnu(@NotNull @NotBlank @NotEmpty @PathVariable String rnu) {
        return requirementsService.getAllRequirementsByRnu(rnu);
    }

    @GetMapping("/getAllRequirementsByCa/{ca}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByCa(@NotNull @NotBlank @NotEmpty @PathVariable String ca) {
        return requirementsService.getAllRequirementsByCa(ca);
    }

    @GetMapping("/getAllRequirementsByAuthor/{author}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByAuthor(@NotNull @NotBlank @NotEmpty @PathVariable String author) {
        return requirementsService.getAllRequirementsByAuthor(author);
    }

    @GetMapping("/getAllRequirementsByReason/{reason}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByReason(@NotNull @NotBlank @NotEmpty @PathVariable String reason) {
        return requirementsService.getAllRequirementsByReason(reason);
    }
    //=============================================================================================================================


    //поиск по датам:
    @GetMapping("/getAllRequirementsByCreationYear/{year}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByCreationYear(@NotNull @Positive @PathVariable Integer year) {
        return requirementsService.getAllRequirementsByCreationYear(year);
    }

    @GetMapping("/getAllRequirementsByCreationYearAndMonth/{month}/{year}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByCreationYearAndMonth(
            @NotNull @Positive @PathVariable Integer month,
            @NotNull @Positive @PathVariable Integer year
    ) {
        return requirementsService.getAllRequirementsByCreationYearAndMonth(month, year);
    }

    @GetMapping("/getAllRequirementsByCreationDate/{day}/{month}/{year}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByCreationDate(
            @NotNull @Positive @PathVariable Integer day,
            @NotNull @Positive @PathVariable Integer month,
            @NotNull @Positive @PathVariable Integer year
    ) {
        return requirementsService.getAllRequirementsByCreationDate(day, month, year);
    }

    @GetMapping("/getAllRequirementsByRealizationYear/{year}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByRealizationYear(@NotNull @Positive @PathVariable Integer year) {
        return requirementsService.getAllRequirementsByRealizationYear(year);
    }

    @GetMapping("/getAllRequirementsByRealizationYearAndMonth/{month}/{year}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByRealizationYearAndMonth(
            @NotNull @Positive @PathVariable Integer month,
            @NotNull @Positive @PathVariable Integer year
    ) {
        return requirementsService.getAllRequirementsByRealizationYearAndMonth(month, year);
    }

    @GetMapping("/getAllRequirementsByRealizationDate/{day}/{month}/{year}")
    public ResponseEntity<List<Requirement>> getAllRequirementsByRealizationDate(
            @NotNull @Positive @PathVariable Integer day,
            @NotNull @Positive @PathVariable Integer month,
            @NotNull @Positive @PathVariable Integer year
    ) {
        return requirementsService.getAllRequirementsByRealizationDate(day, month, year);
    }

    //=============================================================================================================================


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().stream()
                .forEach(error -> {
                    String fieldName = ((FieldError)error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
