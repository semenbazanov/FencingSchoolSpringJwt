package com.semenbazanov.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"apprentice_id", "date"})})
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int numberGym;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "apprentice_id", nullable = false)
    private Apprentice apprentice;

    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    private LocalTime timeStart;
}
