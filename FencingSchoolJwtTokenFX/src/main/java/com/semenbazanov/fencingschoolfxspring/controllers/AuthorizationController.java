package com.semenbazanov.fencingschoolfxspring.controllers;

import com.semenbazanov.fencingschoolfxspring.App;
import com.semenbazanov.fencingschoolfxspring.model.Apprentice;
import com.semenbazanov.fencingschoolfxspring.model.Trainer;
import com.semenbazanov.fencingschoolfxspring.model.User;
import com.semenbazanov.fencingschoolfxspring.retrofit.ApprenticeRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.AuthenticationRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.TrainerRepository;
import com.semenbazanov.fencingschoolfxspring.retrofit.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Base64;
import java.util.prefs.Preferences;

public class AuthorizationController {
    public TextField login;
    public TextField password;

    public void toEnter(ActionEvent actionEvent) {
        if (this.login.getText().isEmpty()) {
            App.showAlert("Error", "Введите логин", Alert.AlertType.ERROR);
            return;
        }
        if (this.password.getText().isEmpty()) {
            App.showAlert("Error", "Введите пароль", Alert.AlertType.ERROR);
            return;
        }
        try {
            String login = this.login.getText();
            String password = this.password.getText();
            AuthenticationRepository authenticationRepository = new AuthenticationRepository(null);
            //получение токена
            String token = authenticationRepository.authentication(login, password);
            String secret = "fencingschooljwt";
            secret = Base64.getEncoder().encodeToString(secret.getBytes());
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            //получение роли
            String role = (String) claims.getBody().get("role");
            //получение юзера
            UserRepository userRepository = new UserRepository(token);
            User user = userRepository.get();

            Preferences preferences = Preferences.userRoot();
            preferences.put("token", token);
            switch (role) {
                case "ADMIN" -> {
                    App.openWindow("admin_main_form.fxml", "admin", user);
                    App.closeWindow(actionEvent);
                }
                case "APPRENTICE" -> {
                    ApprenticeRepository apprenticeRepository = new ApprenticeRepository(token);
                    Apprentice apprentice = apprenticeRepository.get(user.getId());
                    App.openWindow("apprentice_main_form.fxml", "apprentice", apprentice);
                    App.closeWindow(actionEvent);
                }
                case "TRAINER" -> {
                    TrainerRepository trainerRepository = new TrainerRepository(token);
                    Trainer trainer = trainerRepository.get(user.getId());
                    App.openWindow("trainer_main_form.fxml", "trainer", trainer);
                    App.closeWindow(actionEvent);
                }
            }
        } catch (IOException e) {
            App.showAlert("Error", "Пользователь не существует", Alert.AlertType.ERROR);
        }
    }
}
