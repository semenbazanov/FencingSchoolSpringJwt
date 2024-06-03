module com.semenbazanov.fencingschoolfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires retrofit2;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires okhttp3;
    requires retrofit2.converter.jackson;
    requires jjwt;


    opens com.semenbazanov.fencingschoolfxspring to javafx.fxml;
    exports com.semenbazanov.fencingschoolfxspring;
    exports com.semenbazanov.fencingschoolfxspring.controllers;
    opens com.semenbazanov.fencingschoolfxspring.controllers to javafx.fxml;
    exports com.semenbazanov.fencingschoolfxspring.dto to com.fasterxml.jackson.databind;
    exports com.semenbazanov.fencingschoolfxspring.model to com.fasterxml.jackson.databind;
    opens com.semenbazanov.fencingschoolfxspring.model to javafx.base;
}