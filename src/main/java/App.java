import controllers.Controller;
import controllers.HomePageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Meta;

import java.io.IOException;

public class App extends Application{
    private Stage primaryStage;

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initRoot();
    }

    private void initRoot() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/HomePage.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Controller controller = loader.getController();
        controller.setStage(primaryStage);
        loader.setController(controller);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Test Simulator");
        primaryStage.show();
    }
}
