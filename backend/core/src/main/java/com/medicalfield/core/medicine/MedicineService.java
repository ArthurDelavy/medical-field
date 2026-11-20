package com.medicalfield.core.medicine;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.medicalfield.core.exceptions.custom.DuplicatedValueException;
import com.medicalfield.core.exceptions.custom.ElementNotFoundException;
import com.medicalfield.core.medicine.dto.NewMedicineRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
@Validated 
@Transactional(readOnly = true)
public class MedicineService {
    
    private final MedicineRepository mdcRep;

    public Medicine getById(int id) {
        return mdcRep.findById(id).orElseThrow(() -> new ElementNotFoundException("Medicamento não encontrado."));
    }

    public List<Medicine> listAll() {
        return mdcRep.findAll();
    }


    @Transactional
    public Medicine newMedicine(@Valid NewMedicineRequest data) {

        if (mdcRep.existsByBatchNumber(data.batchNumber())) throw new DuplicatedValueException("Medicamento já cadastrado.");

        Medicine medicine = Medicine.builder()
            .name(data.name())
            .manufacturer(data.manufacturer())
            .batchNumber(data.batchNumber())
            .expiresAt(data.expiresAt())
            .build();

        return mdcRep.save(medicine);
    }
}
