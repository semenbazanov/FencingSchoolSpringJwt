package com.semenbazanov.fencingschoolfxspring;

import com.semenbazanov.fencingschoolfxspring.controllers.ControllerData;
import com.semenbazanov.fencingschoolfxspring.model.Apprentice;
import com.semenbazanov.fencingschoolfxspring.model.Trainer;
import com.semenbazanov.fencingschoolfxspring.model.User;
import com.semenbazanov.fencingschoolfxspring.retrofit.ApprenticeRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.TrainerRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.UserRepository;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.prefs.Preferences;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Preferences preferences = Preferences.userRoot();
        String token = preferences.get("token", "empty");
        if (token.equals("empty")) {
            App.openWindow("authorization.fxml", "Authorization", null);
        } else {
            try {
                UserRepository userRepository = new UserRepository(token);
                User user = userRepository.get();
                switch (user.getRole()) {
                    case ("ADMIN") -> {
                        App.openWindow("admin_main_form.fxml", "admin", user);
                    }
                    case ("TRAINER") -> {
                        TrainerRepository trainerRepository = new TrainerRepository(token);
                        Trainer trainer = trainerRepository.get(user.getId());
                        System.out.println(trainer);
                        App.openWindow("trainer_main_form.fxml", "trainer", trainer);
                    }
                    case ("APPRENTICE") -> {
                        ApprenticeRepository apprenticeRepository = new ApprenticeRepository(token);
                        Apprentice apprentice = apprenticeRepository.get(user.getId());
                        System.out.println(apprentice);
                        App.openWindow("apprentice_main_form.fxml", "apprentice", apprentice);
                    }
                }
            } catch (IOException e) {
                App.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static <T> Stage getStage(String name, String title, T data) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(name));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );

        stage.setTitle(title);

        if (data != null) {
            ControllerData<T> controller = loader.getController();
            controller.initData(data);
        }
        return stage;
    }

    public static <T> Stage openWindow(String name, String title, T data) throws IOException {
        Stage stage = getStage(name, title, data);
        stage.show();
        return stage;
    }

    public static <T> Stage openWindowAndWait(String name, String title, T data) throws IOException {
        Stage stage = getStage(name, title, data);
        stage.showAndWait();
        return stage;
    }

    public static void closeWindow(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

//Админ - логин Admin, пароль Admin
//Тренер - логин Trainer, пароль Trainer
//Ученик - логин Apprentice, пароль Apprentice