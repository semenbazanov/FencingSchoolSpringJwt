package com.semenbazanov.fencingschoolfxspring.controllers;

import com.semenbazanov.fencingschoolfxspring.App;
import com.semenbazanov.fencingschoolfxspring.model.*;
import com.semenbazanov.fencingschoolfxspring.retrofit.ApprenticeRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.TrainerRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.TrainerScheduleRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class TrainerMainController implements ControllerData<Trainer> {
    public TableView<TrainerScheduleItem> scheduleView;
    public TextField surname;
    public TextField name;
    public TextField patronymic;
    public TextField experience;
    public ListView<Apprentice> apprentice_list;
    private Trainer trainer;

    @Override
    public void initData(Trainer value) {
        this.trainer = value;

        this.name.setText(this.trainer.getName());
        this.surname.setText(this.trainer.getSurname());
        this.patronymic.setText(this.trainer.getPatronymic());
        this.experience.setText(String.valueOf(this.trainer.getExperience()));

        try {
            this.initListView();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }

        this.apprentice_list.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Apprentice apprentice = this.apprentice_list.getSelectionModel().getSelectedItem();
                try {
                    App.openWindowAndWait("apprentice_from_trainer.fxml", "apprentice", apprentice);
                    this.initListView();
                } catch (IOException e) {
                    App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });

        try {
            this.initTable();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void initListView() throws IOException {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");
        ApprenticeRepository apprenticeRepository = new ApprenticeRepository(token);
        List<Apprentice> apprentices = apprenticeRepository.get();
        this.apprentice_list.setItems(FXCollections.observableArrayList(apprentices));
    }

    public void initTable() throws IOException {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        TableColumn<TrainerScheduleItem, String> dayColumn = new TableColumn<>("День недели");
        TableColumn<TrainerScheduleItem, LocalTime> startTimeColumn = new TableColumn<>("Время начала работы");
        TableColumn<TrainerScheduleItem, LocalTime> endTimeColumn = new TableColumn<>("Время окончания работы");
        this.scheduleView.getColumns().setAll(dayColumn, startTimeColumn, endTimeColumn);

        dayColumn.setCellValueFactory(new PropertyValueFactory<>("engDay"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

        TrainerScheduleRepository repository = new TrainerScheduleRepository(token);
        try {
            TrainerSchedule trainerSchedule = repository.get(trainer.getId());
            List<TrainerScheduleItem> trainerScheduleItems = trainerSchedule.get();
            this.scheduleView.setItems(FXCollections.observableArrayList(trainerScheduleItems));
        } catch (IllegalArgumentException e) {
            this.scheduleView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        }
    }

    public void toUpdateTrainer(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        this.trainer.setSurname(this.surname.getText());
        this.trainer.setName(this.name.getText());
        this.trainer.setPatronymic(this.patronymic.getText());
        this.trainer.setExperience(Integer.parseInt(this.experience.getText()));
        try {
            TrainerRepository trainerRepository = new TrainerRepository(token);
            trainerRepository.update(this.trainer);
            App.showAlert("Information", "Данные обновлены", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toDeleteTrainer(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");
        try {
            TrainerRepository trainerRepository = new TrainerRepository(token);
            trainerRepository.delete(this.trainer.getId());
            App.closeWindow(actionEvent);
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toAddTraining(ActionEvent actionEvent) {
        try {
            App.openWindowAndWait("add_training_schedule.fxml", "add training schedule", this.trainer);
            this.initTable();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toDeleteTraining(ActionEvent actionEvent) {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");

        TrainerScheduleRepository repository = new TrainerScheduleRepository(token);
        TrainerScheduleItem schedule = this.scheduleView.getSelectionModel().getSelectedItem();
        if (schedule == null) {
            App.showAlert("Error", "Запись тренировки не выбрана", Alert.AlertType.INFORMATION);
            return;
        }
        String dayWeek = schedule.getEngDay();
        try {
            repository.delete(this.trainer.getId(), dayWeek);
            this.initTable();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toExit(ActionEvent actionEvent) {
        try {
            Preferences preferences = Preferences.userRoot();
            preferences.remove("token");
            preferences.remove("trainerId");
            App.openWindow("authorization.fxml", "authorization", null);
            App.closeWindow(actionEvent);
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void toAddApprentice(ActionEvent actionEvent) {
        try {
            App.openWindowAndWait("add_apprentice.fxml", "add apprentice", null);
            Preferences preferences = Preferences.userRoot();
            preferences.put("trainerId", String.valueOf(this.trainer.getId()));
            this.initListView();
        } catch (IOException e) {
            App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
