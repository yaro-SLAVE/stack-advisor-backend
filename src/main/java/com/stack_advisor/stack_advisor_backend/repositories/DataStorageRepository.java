package com.stack_advisor.stack_advisor_backend.repositories;

import com.stack_advisor.stack_advisor_backend.models.DataStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataStorageRepository extends JpaRepository<DataStorage, Integer> {
    List<DataStorage> findByNameIn(List<String> names);
}
