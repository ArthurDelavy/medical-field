package com.medicalfield.core.records;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalfield.core.records.dto.MedicalRecordResponse;
import com.medicalfield.core.records.dto.NewMedicalRecordRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/prontuario")
@RequiredArgsConstructor 
@Tag(name = "Prontuario", description = "Cadastro de prontuarios")
public class MedicalRecordController {
    
    private final MedicalRecordService mrcSvc;


    @GetMapping
    @Operation(summary = "Lista todas os prontuários")
        @SecurityRequirement(name = "BearerToken")
        @ApiResponse(responseCode = "200", description = "Listado com sucesso!")
        @ApiResponse(responseCode = "401", description = "Token ausente ou inválido", content = @Content)
    public ResponseEntity<List<MedicalRecordResponse>> listAll() {
        List<MedicalRecordResponse> responseData = mrcSvc.listAll()
                                                .stream()
                                                .map(MedicalRecordResponse::new)
                                                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @GetMapping("/{personId}")
    @Operation(summary = "Obtém um prontuário de uma pessoa específica")
        @SecurityRequirement(name = "BearerToken")
        @ApiResponse(responseCode = "200", description = "Listado com sucesso!")
        @ApiResponse(responseCode = "401", description = "Token ausente ou inválido", content = @Content)
        @ApiResponse(responseCode = "404", description = "Pessoa e/ou prontuario não encontrados", content = @Content)
    public ResponseEntity<MedicalRecordResponse> getByPerson(@PathVariable int personId) {
        MedicalRecordResponse responseData = new MedicalRecordResponse(mrcSvc.getByPerson(personId)); 
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @PostMapping
    @Operation(summary = "Inserir novo prontuário")
        @SecurityRequirement(name = "BearerToken")
        @ApiResponse(responseCode = "201", description = "Criado com sucesso!")
        @ApiResponse(responseCode = "400", description = "Um ou mais campos estão com valores inválidos", content = @Content)
        @ApiResponse(responseCode = "401", description = "Token ausente ou inválido", content = @Content)
        @ApiResponse(responseCode = "409", description = "Prontuario já cadastrado para essa pessoa", content = @Content)
    public ResponseEntity<MedicalRecordResponse> newRecord(@RequestBody @Valid NewMedicalRecordRequest data) {
        MedicalRecordResponse responseData = new MedicalRecordResponse(mrcSvc.newRecord(data));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
    }
}
