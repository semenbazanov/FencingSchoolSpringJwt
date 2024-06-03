package com.semenbazanov.fencingschoolfxspring.controllers;

import com.semenbazanov.fencingschoolfxspring.App;
import com.semenbazanov.fencingschoolfxspring.model.Apprentice;
import com.semenbazanov.fencingschoolfxspring.model.Trainer;
import com.semenbazanov.fencingschoolfxspring.model.User;
import com.semenbazanov.fencingschoolfxspring.retrofit.AdminRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.ApprenticeRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.TrainerRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

public class AdminMainForm implements ControllerData<User> {
    public Label label;
    public ListView<Apprentice> apprenticesList;
    public ListView<Trainer> trainersList;
    private User user;

    @Override
    public void initData(User value) {
        this.user = value;
        this.label.setText(this.label.getText() + value.getName());

        try {
            this.initListView();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }

        this.apprenticesList.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Apprentice apprentice = this.apprenticesList.getSelectionModel().getSelectedItem();
                try {
                    App.openWindowAndWait("apprentice_from_admin.fxml", "apprentice", apprentice);
                    this.initListView();
                } catch (IOException e) {
                    App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });

        this.trainersList.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Trainer trainer = trainersList.getSelectionModel().getSelectedItem();
                try {
                    App.openWindowAndWait("trainer_from_admin.fxml", "trainer", trainer);
                    this.initListView();
                } catch (IOException e) {
                    App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    public void initListView() throws IOException {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        ApprenticeRepository apprenticeRepository = new ApprenticeRepository(token);
        List<Apprentice> apprentices = apprenticeRepository.get();
        this.apprenticesList.setItems(FXCollections.observableArrayList(apprentices));

        TrainerRepository trainerRepository = new TrainerRepository(token);
        List<Trainer> trainers = trainerRepository.get();
        this.trainersList.setItems(FXCollections.observableArrayList(trainers));
    }

    public void toDeleteUser(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");
        try {
            AdminRepository adminRepository = new AdminRepository(token);
            adminRepository.delete(this.user.getId());
            preferences.remove("token");
            App.openWindow("authorization.fxml", "authorization", null);
            App.closeWindow(actionEvent);
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toExit(ActionEvent actionEvent) {
        try {
            App.openWindow("authorization.fxml", "Authorization", null);
            App.closeWindow(actionEvent);
            Preferences preferences = Preferences.userRoot();
            preferences.remove("token");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void toAdd(ActionEvent actionEvent) {
        try {
            App.openWindowAndWait("add.fxml", "add", null);
            this.initListView();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toAddAdmin(ActionEvent actionEvent) {
        try {
            App.openWindow("add_admin.fxml", "add admin", null);
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
