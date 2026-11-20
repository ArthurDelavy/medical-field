package com.medicalfield.core.medicine;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalfield.core.medicine.dto.MedicineResponse;
import com.medicalfield.core.medicine.dto.NewMedicineRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping("/medicine")
@Tag(name = "Medicine", description = "Cadastro e consulta de medicamentos")
public class MedicineController {
    
    private final MedicineService mdcSvc;


    @GetMapping
    public ResponseEntity<List<MedicineResponse>> getAll() {
        List<MedicineResponse> responseData = mdcSvc.listAll()
                                                .stream()
                                                .map(MedicineResponse::new)
                                                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
    

    @GetMapping("/{medicineId}")
    public ResponseEntity<MedicineResponse> getById(@PathVariable int medicineId) {
        MedicineResponse responseData = new MedicineResponse(mdcSvc.getById(medicineId));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    
    @PostMapping
    public ResponseEntity<MedicineResponse> newMedicine(@RequestBody @Valid NewMedicineRequest data) {
        MedicineResponse responseData = new MedicineResponse(mdcSvc.newMedicine(data));
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
