package com.semenbazanov.fencingschoolfxspring.controllers;

import com.semenbazanov.fencingschoolfxspring.App;
import com.semenbazanov.fencingschoolfxspring.model.Apprentice;
import com.semenbazanov.fencingschoolfxspring.model.Trainer;
import com.semenbazanov.fencingschoolfxspring.model.User;
import com.semenbazanov.fencingschoolfxspring.retrofit.ApprenticeRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.TrainerRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AddController {

    public RadioButton trainerButton;
    public RadioButton apprenticeButton;
    public TextField surname;
    public Label additionalLabel;
    public TextField name;
    public TextField patronymic;

    public TextField password;
    public TextField login;
    public TextField additionalTextField;

    public void initialize(){
        ToggleGroup group = new ToggleGroup();
        this.trainerButton.setToggleGroup(group);
        this.apprenticeButton.setToggleGroup(group);
        this.apprenticeButton.setSelected(true);
    }

    public void toPutTrainer(ActionEvent actionEvent) {
        this.additionalLabel.setText("Опыт в годах");
    }

    public void toPutApprentice(ActionEvent actionEvent) {
        this.additionalLabel.setText("Номер телефона");
    }

    public void toCreate(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        if (this.apprenticeButton.isSelected()) {
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
            if (this.additionalTextField.getText().isEmpty()) {
                App.showAlert("Error", "Введите номер телефона", Alert.AlertType.INFORMATION);
                return;
            }
            //здесь логин и пароль для создания нового пользователя
            Apprentice apprentice = new Apprentice(this.login.getText(), this.password.getText(), this.name.getText(),
                    this.surname.getText(), this.patronymic.getText(), "APPRENTICE", this.additionalTextField.getText());
            try {
                //здесь логин и пароль для работы ролей
                ApprenticeRepository apprenticeRepository = new ApprenticeRepository(token);
                apprenticeRepository.post(apprentice);
                App.closeWindow(actionEvent);
            } catch (IOException | IllegalArgumentException e) {
                App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
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
            if (this.additionalTextField.getText().isEmpty()) {
                App.showAlert("Error", "Введите опыт работы", Alert.AlertType.INFORMATION);
                return;
            }
            Trainer trainer = new Trainer(this.login.getText(), this.password.getText(), this.name.getText(),
                    this.surname.getText(), this.patronymic.getText(), "TRAINER", Integer.parseInt(this.additionalTextField.getText()));
            try {
                TrainerRepository trainerRepository = new TrainerRepository(token);
                trainerRepository.post(trainer);
                App.closeWindow(actionEvent);
            } catch (IOException | IllegalArgumentException e) {
                App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
