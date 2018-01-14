package gui;

import gui.dictionary.AppConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage rootStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(AppConstants.INTRO_STAGE));
        primaryStage.setTitle("Szóstki");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(AppConstants.APP_ICON_URL));
        rootStage = primaryStage;
        primaryStage.show();
    }

    public static void replaceStage(String fxmlDocName) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource(AppConstants.GUI_PACKAGE_URL + fxmlDocName));
        rootStage.setScene(new Scene(root));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
