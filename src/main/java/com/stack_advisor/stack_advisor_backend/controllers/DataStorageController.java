package com.stack_advisor.stack_advisor_backend.controllers;

import com.stack_advisor.stack_advisor_backend.models.DataStorage;
import com.stack_advisor.stack_advisor_backend.requests.DataStorageCreatingRequest;
import com.stack_advisor.stack_advisor_backend.responses.DataStoragesListResponse;
import com.stack_advisor.stack_advisor_backend.services.DataStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/datastorage")
@CrossOrigin(origins = "*")
public class DataStorageController {
    @Autowired
    public DataStorageService dataStorageService;

    @GetMapping
    public ResponseEntity<?> getDataStorages() {
        List<DataStorage> dataStorages = dataStorageService.getAllDataStorages();

        ArrayList<DataStoragesListResponse> response = new ArrayList<>();

        for (DataStorage dataStorage : dataStorages) {
            response.add(new DataStoragesListResponse(
                    dataStorage.getId(),
                    dataStorage.getName(),
                    dataStorage.getStorageType(),
                    dataStorage.getStorageLocation(),
                    dataStorage.getDataBaseType()
            ));
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createDataStorage(@RequestBody DataStorageCreatingRequest request) {
        DataStorage dataStorage = dataStorageService.createDataStorage(
                request.getName(),
                request.getStorageType(),
                request.getStorageLocation(),
                request.getDataBaseType()
        );

        return ResponseEntity.ok(new DataStoragesListResponse(
                dataStorage.getId(),
                dataStorage.getName(),
                dataStorage.getStorageType(),
                dataStorage.getStorageLocation(),
                dataStorage.getDataBaseType()
        ));
    }
}
