package com.stack_advisor.stack_advisor_backend.models;

import com.stack_advisor.stack_advisor_backend.models.enums.language.EntryThreshold;
import com.stack_advisor.stack_advisor_backend.models.enums.language.ExecutionModel;
import com.stack_advisor.stack_advisor_backend.models.enums.language.Popularity;
import com.stack_advisor.stack_advisor_backend.models.enums.language.Purpose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "language",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "name")
        })
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="entry_treshold")
    private EntryThreshold entryThreshold;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="execution_model")
    private ExecutionModel executionModel;
    @Enumerated(EnumType.ORDINAL)
    private Popularity popularity;
    private Purpose purpose;
}
