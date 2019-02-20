
import Game.Controller.controller;
import Game.Model.model;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage){

        //Model erzeugen und Stage übergeben
        model myModel = new model(primaryStage);
        //Controller erzeugen und Model übergeben
        controller myController = new controller(myModel);

        //Controller-Methode zur Anzeige des Views aufrufen
        myController.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
