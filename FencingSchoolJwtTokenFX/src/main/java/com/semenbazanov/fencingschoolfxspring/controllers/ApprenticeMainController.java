package com.semenbazanov.fencingschoolfxspring.controllers;

import com.semenbazanov.fencingschoolfxspring.App;
import com.semenbazanov.fencingschoolfxspring.model.Apprentice;
import com.semenbazanov.fencingschoolfxspring.model.Training;
import com.semenbazanov.fencingschoolfxspring.model.User;
import com.semenbazanov.fencingschoolfxspring.retrofit.ApprenticeRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.TrainingRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class ApprenticeMainController implements ControllerData<Apprentice> {
    public TextField surname;
    public TextField name;
    public TextField patronymic;
    public TextField phoneNumber;
    public ListView<Training> trainingsListView;

    private Apprentice apprentice;

    @Override
    public void initData(Apprentice value) {
        this.apprentice = value;

        this.name.setText(this.apprentice.getName());
        this.surname.setText(this.apprentice.getSurname());
        this.patronymic.setText(this.apprentice.getPatronymic());
        this.phoneNumber.setText(String.valueOf(this.apprentice.getPhoneNumber()));

        try {
            this.initListView();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
        }
    }

    public void initListView() throws IOException {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        TrainingRepository repository = new TrainingRepository(token);
        List<Training> trainings = repository.get(this.apprentice.getId());
        this.trainingsListView.setItems(FXCollections.observableArrayList(trainings));
    }

    public void toUpdate(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        this.apprentice.setSurname(this.surname.getText());
        this.apprentice.setName(this.name.getText());
        this.apprentice.setPatronymic(this.patronymic.getText());
        this.apprentice.setPhoneNumber(this.phoneNumber.getText());

        try {
            ApprenticeRepository apprenticeRepository = new ApprenticeRepository(token);
            apprenticeRepository.update(this.apprentice);
            App.showAlert("Information", "Данные обновлены", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            App.showAlert("Error", "Ученик уже существует", Alert.AlertType.ERROR);

        }
    }

    public void toDelete(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        try {
            ApprenticeRepository apprenticeRepository = new ApprenticeRepository(token);
            apprenticeRepository.delete(this.apprentice.getId());
            App.closeWindow(actionEvent);
            preferences.remove("token");
            App.openWindow("authorization.fxml", "authorization", null);
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toAddTraining(ActionEvent actionEvent) {
        try {
            App.openWindowAndWait("add_training.fxml", "add training", this.apprentice);
            this.initListView();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toDeleteTraining(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        Training training = this.trainingsListView.getSelectionModel().getSelectedItem();
        if (training == null) {
            App.showAlert("Error", "Тренировка не выбрана", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            TrainingRepository repository = new TrainingRepository(token);
            repository.delete(training.getId());

            this.initListView();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toExit(ActionEvent actionEvent) {
        try {
            Preferences preferences = Preferences.userRoot();
            preferences.remove("token");
            App.openWindow("authorization.fxml", "authorization", null);
            App.closeWindow(actionEvent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
