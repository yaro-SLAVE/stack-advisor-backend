package com.stack_advisor.stack_advisor_backend.models;

import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.DataBaseType;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.StorageLocation;
import com.stack_advisor.stack_advisor_backend.models.enums.dataStorage.StorageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "data_storage",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "name")
        })
public class DataStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="storage_type")
    private StorageType storageType;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="storage_location")
    private StorageLocation storageLocation;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="database_type")
    private DataBaseType dataBaseType;

    public DataStorage(String name, StorageType storageType, StorageLocation storageLocation, DataBaseType dataBaseType) {
        this.name = name;
        this.storageType = storageType;
        this.storageLocation = storageLocation;
        this.dataBaseType = dataBaseType;
    }
}
