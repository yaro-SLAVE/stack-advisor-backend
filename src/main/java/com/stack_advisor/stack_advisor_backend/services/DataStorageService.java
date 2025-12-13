package com.stack_advisor.stack_advisor_backend.services;

import com.stack_advisor.stack_advisor_backend.models.DataStorage;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.DataBaseType;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.StorageLocation;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.StorageType;
import com.stack_advisor.stack_advisor_backend.repositories.DataStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataStorageService {
    @Autowired
    public DataStorageRepository DataStorageRepository;

    public List<DataStorage> getAllDataStorages() { return DataStorageRepository.findAll(); }

    public DataStorage createDataStorage(String name, StorageType storageType, StorageLocation storageLocation, DataBaseType dataBaseType) {
        DataStorage dataStorage = new DataStorage(name, storageType, storageLocation, dataBaseType);
        dataStorage = DataStorageRepository.save(dataStorage);
        return dataStorage;
    }
}
