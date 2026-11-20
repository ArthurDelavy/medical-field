package com.medicalfield.core.person;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalfield.core.person.dto.NewPersonRequest;
import com.medicalfield.core.person.dto.PersonResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pessoa")
@Tag(name = "Pessoa", description = "Cadastro de pessoas")
public class PersonController {
    
    private final PersonService psnSvc;


    @GetMapping
    @Operation(summary = "Lista todas as pessoas")
        @SecurityRequirement(name = "BearerToken")
        @ApiResponse(responseCode = "200", description = "Listado com sucesso!")
        @ApiResponse(responseCode = "401", description = "Token ausente ou inválido", content = @Content)
    public ResponseEntity<List<PersonResponse>> listAll() {
        List<PersonResponse> responseData = psnSvc.listAll()
                                                .stream()
                                                .map(PersonResponse::new)
                                                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @GetMapping("/{personId}")
    @Operation(summary = "Lista todos os dados de uma pessoa específica")
        @SecurityRequirement(name = "BearerToken")
        @ApiResponse(responseCode = "200", description = "Listado com sucesso!")
        @ApiResponse(responseCode = "401", description = "Token ausente ou inválido", content = @Content)
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada", content = @Content)
    public ResponseEntity<PersonResponse> getById(@PathVariable int personId) {
        PersonResponse responseData = new PersonResponse(psnSvc.getById(personId)); 
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @PostMapping
    @Operation(summary = "Inserir nova pessoa")
        @SecurityRequirement(name = "BearerToken")
        @ApiResponse(responseCode = "201", description = "Criado com sucesso!")
        @ApiResponse(responseCode = "400", description = "Um ou mais campos estão com valores inválidos", content = @Content)
        @ApiResponse(responseCode = "401", description = "Token ausente ou inválido", content = @Content)
        @ApiResponse(responseCode = "409", description = "Pessoa já cadastrada", content = @Content)
    public ResponseEntity<PersonResponse> newPerson(@RequestBody @Valid NewPersonRequest data) {
        PersonResponse responseData = new PersonResponse(psnSvc.newPerson(data));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }
}
