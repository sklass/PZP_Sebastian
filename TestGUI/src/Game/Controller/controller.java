package Game.Controller;
//Im Controller befindet sich die Programm-Logik. Dienst als Schnittstelle zwischen Model und View

import Game.Model.model;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class controller {
    model Model;


    //Beim erzeugen des Controllers wird das Model übergeben
    public controller(model MyModel){
        this.Model = MyModel;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/sample.fxml"));
        //fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void show(){
        Model.getPrimaryStage().show();

    }
}
