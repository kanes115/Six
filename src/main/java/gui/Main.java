package gui;

import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static Integer ROWS = 4;
    private static Integer COLUMNS = 8;

    private VBox root = new VBox();


    private void initBoard() {
        for (int i = 0; i < ROWS; i++) {
            HBox hbox = new HBox(COLUMNS);
            for (int j = 0; j < COLUMNS; j++){
                ImageButton image =  new ImageButton(String.format("/gui/cards/%s_of_clubs.png", j+2));
                image.resize(50,50);

                hbox.getChildren().add(image);
            }
            root.getChildren().add(hbox);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        root = FXMLLoader.load(getClass().getResource("/gui/main.fxml"));
        primaryStage.setTitle("Szóstki");
        initBoard();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/gui/icon.jpg")));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
