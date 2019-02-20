package Game.Model;
//Das Model speichert alle Daten der Anwendung

import javafx.stage.Stage;

public class model {
    Stage primaryStage = null;

    public model(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
