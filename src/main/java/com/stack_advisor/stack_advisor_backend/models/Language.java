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
    @Enumerated(EnumType.STRING)
    @Column(name="entry_threshold")
    private EntryThreshold entryThreshold;
    @Enumerated(EnumType.STRING)
    @Column(name="execution_model")
    private ExecutionModel executionModel;
    @Enumerated(EnumType.STRING)
    private Popularity popularity;
    @Enumerated(EnumType.STRING)
    private Purpose purpose;

    public Language(String name, EntryThreshold entryThreshold, ExecutionModel executionModel, Popularity popularity, Purpose purpose) {
        this.name = name;
        this.entryThreshold = entryThreshold;
        this.executionModel = executionModel;
        this.popularity = popularity;
        this.purpose = purpose;
    }
}
