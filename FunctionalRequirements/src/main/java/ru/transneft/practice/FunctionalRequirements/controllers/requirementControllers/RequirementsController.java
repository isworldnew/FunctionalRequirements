package ru.transneft.practice.FunctionalRequirements.controllers.requirementControllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.transneft.practice.FunctionalRequirements.dto.RequirementRegistrationDto;
import ru.transneft.practice.FunctionalRequirements.entities.Requirement;
import ru.transneft.practice.FunctionalRequirements.services.RequirementsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/requirements")
@CrossOrigin(origins = "http://localhost:4004")
public class RequirementsController {

    private final RequirementsService requirementsService;

    @Autowired
    public RequirementsController(RequirementsService requirementsService) {
        this.requirementsService = requirementsService;
    }

    @PostMapping("/saveNewFunctionalRequirement")
    public ResponseEntity<Requirement> saveNewFunctionalRequirement(@Valid @RequestBody RequirementRegistrationDto dto) {
        return requirementsService.saveNewFunctionalRequirement(dto);
    }


    @GetMapping("/getAllFunctionalRequirements")
    public ResponseEntity<List<Requirement>> getAllRequirements() {
        return requirementsService.getAllRequirements();
    }

    @PutMapping("/updateExistingFunctionalRequirement")
    public ResponseEntity<Requirement> updateExistingFunctionalRequirement(@Valid @RequestBody Requirement requirement) {
        return requirementsService.updateExistingFunctionalRequirement(requirement);
    }

    @DeleteMapping("/deleteFunctionalRequirementById/{id}")
    public ResponseEntity<Void> deleteFunctionalRequirementById(@NotNull @NotBlank @NotEmpty @PathVariable String id) {
        return requirementsService.deleteFunctionalRequirementById(id);
    }


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
