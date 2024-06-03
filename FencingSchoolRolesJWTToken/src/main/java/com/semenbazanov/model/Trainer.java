package com.semenbazanov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainers")
public class Trainer extends User{
    @Column(nullable = false)
    private int experience;

    @OneToOne(mappedBy = "trainer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private TrainerSchedule schedule;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Training> trainings;
}
