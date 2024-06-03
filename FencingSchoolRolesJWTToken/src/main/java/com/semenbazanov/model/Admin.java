package com.semenbazanov.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User{
    private String company;
}
