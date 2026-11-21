package com.medicalfield.core.records;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.medicalfield.core.exceptions.custom.DuplicatedValueException;
import com.medicalfield.core.exceptions.custom.ElementNotFoundException;
import com.medicalfield.core.person.Person;
import com.medicalfield.core.person.PersonService;
import com.medicalfield.core.records.dto.NewMedicalRecordRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated 
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicalRecordService {
    
    private final MedicalRecordRepository mrcRep;

    private final PersonService psnSvc;



    public List<MedicalRecord> listAll() {
        return mrcRep.findAll();
    }

    public MedicalRecord getByPerson(int personId) {
        Person person = psnSvc.getById(personId);
        return mrcRep.findByPerson(person).orElseThrow(() -> new ElementNotFoundException("Erro ao buscar prontuário da pessoa."));
    }


    public MedicalRecord newRecord(@Valid NewMedicalRecordRequest data) {

        MedicalRecord savedRecord = mrcRep.findBySyncId(data.syncId()).orElse(null);
        if (savedRecord != null) return savedRecord;
        
        Person person = psnSvc.getById(data.personId());

        if (mrcRep.existsByPerson(person)) throw new DuplicatedValueException("Pessoa já possui prontuário cadastrado.");

        MedicalRecord record = MedicalRecord.builder()
            .person(person)
            .bloodType(data.bloodType())
            .allergies(data.allergies())
            .comorbidities(data.comorbidities())
            .continuousUseMedicines(data.continuousUseMedicines())
            .generalObservations(data.generalObservations())
            .createdAt(data.createdAt())
            .syncId(data.syncId())
            .build();

        return mrcRep.save(record);
    }
}
