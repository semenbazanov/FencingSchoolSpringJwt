package com.semenbazanov.fencingschoolfxspring.controllers;

import com.semenbazanov.fencingschoolfxspring.App;
import com.semenbazanov.fencingschoolfxspring.model.Apprentice;
import com.semenbazanov.fencingschoolfxspring.model.Training;
import com.semenbazanov.fencingschoolfxspring.retrofit.TrainingRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class ApprenticeFromTrainerController implements ControllerData<Apprentice> {
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
        }
    }

    public void initListView() throws IOException {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        TrainingRepository repository = new TrainingRepository(token);
        List<Training> trainings = repository.get(this.apprentice.getId());
        this.trainingsListView.setItems(FXCollections.observableArrayList(trainings));
    }

    public void toAddTraining(ActionEvent actionEvent) {
        try {
            App.openWindowAndWait("add_training.fxml", "add training", this.apprentice);
            this.initListView();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
