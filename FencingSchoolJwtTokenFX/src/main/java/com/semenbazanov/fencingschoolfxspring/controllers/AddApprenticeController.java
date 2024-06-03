package com.semenbazanov.fencingschoolfxspring.controllers;

import com.semenbazanov.fencingschoolfxspring.App;
import com.semenbazanov.fencingschoolfxspring.model.Apprentice;
import com.semenbazanov.fencingschoolfxspring.retrofit.ApprenticeRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AddApprenticeController {

    public TextField login;
    public TextField password;
    public TextField name;
    public TextField surname;
    public TextField patronymic;
    public TextField phoneNumber;

    public void toAdd(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

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
        if (this.phoneNumber.getText().isEmpty()) {
            App.showAlert("Error", "Введите номер телефона", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            Apprentice apprentice = new Apprentice(this.login.getText(), this.password.getText(), this.name.getText(),
                    this.surname.getText(), this.patronymic.getText(), "APPRENTICE", this.phoneNumber.getText());
            ApprenticeRepository apprenticeRepository = new ApprenticeRepository(token);
            apprenticeRepository.post(apprentice);
            App.closeWindow(actionEvent);
        } catch (IOException | IllegalArgumentException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
