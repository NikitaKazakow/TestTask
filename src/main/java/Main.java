import db.DbConnectionManager;
import dto.DatabaseCredentials;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        configure();

        Parent rootLayout = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("view/Main.fxml")));

        rootLayout.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        Scene scene = new Scene(rootLayout);
        primaryStage.setTitle("Test task");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configure() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {

            properties.load(fileInputStream);

            String dbPath = properties.getProperty("db.path");
            String dbUsername = properties.getProperty("db.username");
            String dbPassword = properties.getProperty("db.password");

            DbConnectionManager.setCredentials(
                    new DatabaseCredentials(dbPath, dbUsername, dbPassword)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
