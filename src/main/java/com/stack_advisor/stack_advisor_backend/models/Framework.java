package com.stack_advisor.stack_advisor_backend.models;

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
@Table(name = "framework",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id")
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
}
