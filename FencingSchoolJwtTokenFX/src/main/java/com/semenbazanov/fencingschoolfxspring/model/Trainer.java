package com.semenbazanov.fencingschoolfxspring.model;

import java.util.Objects;

public class Trainer extends User{
    private int experience;

    public Trainer() {
    }

    public Trainer(String name, String surname, String patronymic, int experience) {
        super(name, surname, patronymic);
        this.experience = experience;
    }

    public Trainer(String login, String password, String name, String surname,
                   String patronymic, String role, int experience) {
        super(login, password, name, surname, patronymic, role);
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trainer trainer = (Trainer) o;
        return experience == trainer.experience;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), experience);
    }

    @Override
    public String toString() {
        return String.format("Фамилия: %s, Имя: %s, Отчество: %s, Опыт в годах: %d",
                getSurname(), getName(), getPatronymic(), getExperience());
    }
}
