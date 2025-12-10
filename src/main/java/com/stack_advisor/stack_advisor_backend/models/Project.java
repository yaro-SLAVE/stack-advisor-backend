package com.stack_advisor.stack_advisor_backend.models;

import com.stack_advisor.stack_advisor_backend.models.enums.project.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "project",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
        })
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "projects_languages",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages=new ArrayList<>();
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "projects_frameworks",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "framework_id")
    )
    private List<Framework> frameworks=new ArrayList<>();
    @Enumerated(EnumType.ORDINAL)
    @Column(name="time_to_show")
    private TimeToShow timeToShow;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="app_type")
    private AppType appType;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="project_type")
    private ProjectType projectType;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="team_size")
    private TeamSize teamSize;
    @Enumerated(EnumType.ORDINAL)
    private Scale scale;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="data_storage_id")
    private DataStorage dataStorage;
}
