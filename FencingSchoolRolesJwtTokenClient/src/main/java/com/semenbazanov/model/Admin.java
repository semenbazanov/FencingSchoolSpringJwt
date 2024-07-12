package com.semenbazanov.model;

import java.util.Objects;

public class Admin extends User {
    private String company;

    public Admin() {
    }

    public Admin(String login, String password, String name, String surname,
                 String patronymic, String role, String company) {
        super(login, password, name, surname, patronymic, role);
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(company, admin.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "company='" + company + '\'' +
                '}';
    }
}
