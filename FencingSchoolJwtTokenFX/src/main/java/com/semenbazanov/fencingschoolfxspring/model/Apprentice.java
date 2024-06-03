package com.semenbazanov.fencingschoolfxspring.model;

import java.util.Objects;

public class Apprentice extends User{

    private String phoneNumber;

    public Apprentice() {
    }

    public Apprentice(String login, String password, String name, String surname,
                      String patronymic, String role, String phoneNumber) {
        super(login, password, name, surname, patronymic, role);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Apprentice that = (Apprentice) o;
        return Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber);
    }

    @Override
    public String toString() {
        return String.format("Фамилия: %s, Имя: %s, Отчество: %s, Номер телефона: %s",
                getSurname(), getName(), getPatronymic(), this.phoneNumber);
    }
}
