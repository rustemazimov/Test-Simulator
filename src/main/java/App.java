import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application{
    private Stage primaryStage;

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initRoot();
    }

    private void initRoot() throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("views/HomePage.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Test Simulator");
        primaryStage.show();
    }
}
