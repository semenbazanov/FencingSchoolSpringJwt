package com.semenbazanov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "apprenticies")
public class Apprentice extends User{
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "apprentice")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Training> trainings;
}
