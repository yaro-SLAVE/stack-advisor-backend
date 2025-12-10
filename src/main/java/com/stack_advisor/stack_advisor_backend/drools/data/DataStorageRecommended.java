package com.stack_advisor.stack_advisor_backend.drools.data;

import com.stack_advisor.stack_advisor_backend.models.DataStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DataStorageRecommended {
    private DataStorage dataStorage;
    private Double score;
}
