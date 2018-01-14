package gui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by michaello on 14.01.18.
 */
public class GuiTools {

    private GuiTools() {
        //
    }

    public static ImageView createImageView(double width, double height, String imageUrl){
        ImageView imageView = new ImageView(new Image(imageUrl));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public static ImageView createImageView(double layoutX, double layoutY, double width, double height) {
        ImageView imageView = new ImageView();
        setImageViewFields(imageView, layoutX, layoutY, width, height);
        return imageView;
    }

    private static void setImageViewFields(ImageView imageView, double layoutX, double layoutY, double width, double height) {
        setImageViewWidthAndHeight(imageView, width, height);
        imageView.setLayoutX(layoutX);
        imageView.setLayoutY(layoutY);
    }

    private static void setImageViewWidthAndHeight(ImageView imageView, double width, double height){
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public static void showAlertDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
