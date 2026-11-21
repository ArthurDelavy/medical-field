package com.medicalfield.core.person;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    boolean existsByDocument(String document);

    Optional<Person> findBySyncId(UUID syncId);
}
