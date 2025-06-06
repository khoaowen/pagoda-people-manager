package com.pagoda.infrastructure.repository;

import com.pagoda.infrastructure.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
    List<PersonEntity> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastName, String firstName);
}
