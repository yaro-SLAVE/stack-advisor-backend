package com.stack_advisor.stack_advisor_backend.models;

import com.stack_advisor.stack_advisor_backend.models.enums.framework.TasksType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "framework",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "name")
        })
public class Framework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "frameworks_languages",
            joinColumns = @JoinColumn(name = "framework_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages=new ArrayList<>();
    @Column(name="is_reactive")
    private Boolean isReactive;
    @Column(name="last_updated_at")
    private LocalDate lastUpdatedAt;
    @Column(name="is_actual")
    private Boolean isActual;
    @Enumerated(EnumType.STRING)
    @Column(name="tasks_type")
    private TasksType tasksType;

    public Framework(String name, List<Language> languages, Boolean isReactive, LocalDate lastUpdatedAt, TasksType tasksType) {
        this.name = name;
        this.languages = languages;
        this.isReactive = isReactive;
        this.lastUpdatedAt = lastUpdatedAt;
        this.tasksType = tasksType;

        LocalDate now = LocalDate.now();
        this.isActual = (now.getYear() - lastUpdatedAt.getYear()) < 3;
    }

    public void setLastUpdatedAt(LocalDate lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
        LocalDate now = LocalDate.now();
        if (!this.isActual && (now.getYear() - lastUpdatedAt.getYear()) < 3) {
            this.isActual = true;
        }
    }
}
