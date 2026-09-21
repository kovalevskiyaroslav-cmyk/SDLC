package by.bsuir.agecalc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("/view/MainView.fxml"));
        Scene scene = new Scene(loader.load(), 420, 380);
        stage.setTitle("Возраст в неожиданных единицах");
        stage.setScene(scene);
        stage.show();
    }
}