package com.medicalfield.core.records;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.medicalfield.core.person.Person;
import com.medicalfield.core.records.enums.BloodType;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter @Setter @Builder
@DynamicInsert @DynamicUpdate 
@AllArgsConstructor @NoArgsConstructor
@Table(name = "medical_records")
public class MedicalRecord {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne @JoinColumn(name = "person_id")
    private Person person;



    @Enumerated @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private BloodType bloodType;

    private String allergies;

    private String comorbidities;

    private String continuousUseMedicines;

    private String generalObservations;


    private Instant createdAt;

    private UUID syncId;
}
