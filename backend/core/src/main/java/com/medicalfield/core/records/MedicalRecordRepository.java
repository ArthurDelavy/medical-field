package com.medicalfield.core.records;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicalfield.core.person.Person;

@Repository 
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer>{
    
    @EntityGraph(attributePaths = {"person"})
    Optional<MedicalRecord> findByPerson(Person person);

    @EntityGraph(attributePaths = {"person"})
    List<MedicalRecord> findAll();

    @EntityGraph(attributePaths = {"person"})
    Optional<MedicalRecord> findBySyncId(UUID syncId);

    boolean existsByPerson(Person person);
}
