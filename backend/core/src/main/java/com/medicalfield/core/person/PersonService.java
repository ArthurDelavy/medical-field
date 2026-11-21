package com.medicalfield.core.person;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.medicalfield.core.exceptions.custom.DuplicatedValueException;
import com.medicalfield.core.exceptions.custom.ElementNotFoundException;
import com.medicalfield.core.person.dto.NewPersonRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    
    private final PersonRepository psnRep;



    public List<Person> listAll() {
        return psnRep.findAll();
    }

    public Person getById(int id) {
        return psnRep.findById(id).orElseThrow(() -> new ElementNotFoundException("Pessoa não encontrada."));
    }


    @Transactional
    public Person newPerson(@Valid NewPersonRequest data) {
        
        Person savedPerson = psnRep.findBySyncId(data.syncId()).orElse(null);
        if (savedPerson != null) return savedPerson;



        if (data.document() != null)
            if (psnRep.existsByDocument(data.document())) throw new DuplicatedValueException("Pessoa já cadastrada.");

        Person person = Person.builder()
            .name(data.name())
            .gender(data.gender())
            .document(data.document().trim())
            .emergencyContactEmail(data.emergencyContactEmail())
            .syncId(data.syncId())
            .build();

        return psnRep.save(person);
    }
}
