package controllers;

import javafx.stage.Stage;

public abstract class Controller {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
