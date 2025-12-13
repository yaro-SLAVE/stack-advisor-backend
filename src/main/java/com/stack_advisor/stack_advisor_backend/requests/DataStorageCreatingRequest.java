package com.stack_advisor.stack_advisor_backend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.DataBaseType;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.StorageLocation;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.StorageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataStorageCreatingRequest {
    private String name;
    @JsonProperty("storage_type")
    private StorageType storageType;
    @JsonProperty("storage_location")
    private StorageLocation storageLocation;
    @JsonProperty("database_type")
    private DataBaseType dataBaseType;
}
