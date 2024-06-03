package com.semenbazanov.fencingschoolfxspring.controllers;

import com.semenbazanov.fencingschoolfxspring.App;
import com.semenbazanov.fencingschoolfxspring.model.Admin;
import com.semenbazanov.fencingschoolfxspring.retrofit.AdminRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AddAdminController {
    public TextField login;
    public TextField password;
    public TextField name;
    public TextField surname;
    public TextField patronymic;
    public TextField company;

    public void toAdd(ActionEvent actionEvent) {
        if (this.login.getText().isEmpty()) {
            App.showAlert("Error", "Введите логин", Alert.AlertType.INFORMATION);
            return;
        }
        if (this.password.getText().isEmpty()) {
            App.showAlert("Error", "Введите пароль", Alert.AlertType.INFORMATION);
            return;
        }
        if (this.surname.getText().isEmpty()) {
            App.showAlert("Error", "Введите фамилию", Alert.AlertType.INFORMATION);
            return;
        }
        if (this.name.getText().isEmpty()) {
            App.showAlert("Error", "Введите имя", Alert.AlertType.INFORMATION);
            return;
        }
        if (this.patronymic.getText().isEmpty()) {
            App.showAlert("Error", "Введите отчество", Alert.AlertType.INFORMATION);
            return;
        }
        if (this.company.getText().isEmpty()) {
            App.showAlert("Error", "Введите название компании", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            Preferences preferences = Preferences.userRoot();
            String token = preferences.get("token", "empty");
            Admin admin = new Admin(this.login.getText(), this.password.getText(), this.name.getText(),
                    this.surname.getText(), this.patronymic.getText(), "ADMIN", this.company.getText());
            AdminRepository adminRepository = new AdminRepository(token);
            adminRepository.post(admin);
            App.closeWindow(actionEvent);
        } catch (IOException | IllegalArgumentException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
