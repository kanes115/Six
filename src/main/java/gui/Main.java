package gui;

import gui.dictionary.AppConstants;
import gui.i18n.CodesI18n;
import gui.i18n.I18n;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {

    private static Stage rootStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        I18n.getInstance().setLocale(new Locale("pl"));

        Parent root = FXMLLoader.load(getClass().getResource(AppConstants.GAME_STAGE_URL));
        primaryStage.setTitle(I18n.getInstance().getString(CodesI18n.GAME_TITLE));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(AppConstants.APP_ICON_URL));
        rootStage = primaryStage;
        primaryStage.show();
    }

    public static void replaceStage(String fxmlDocUrl) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource(fxmlDocUrl));
        rootStage.setScene(new Scene(root));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
