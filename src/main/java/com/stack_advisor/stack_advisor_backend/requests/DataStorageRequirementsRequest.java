package com.stack_advisor.stack_advisor_backend.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.DataBaseType;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.StorageLocation;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.StorageType;
import lombok.Getter;

@Getter
public class DataStorageRequirementsRequest {
    @JsonProperty("storage_type")
    private StorageType storageType;
    @JsonProperty("storage_location")
    private StorageLocation storageLocation;
    @JsonProperty("data_base_type")
    private DataBaseType dataBaseType;
}
