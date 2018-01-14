package gui.dictionary;

/**
 * Created by michaello on 14.01.18.
 */
public interface AppConstants {

    public final String GUI_PACKAGE_NAME = "gui";

    public final String GUI_PACKAGE_URL = "/" + GUI_PACKAGE_NAME + "/";

    public final String APP_ICON_URL = GUI_PACKAGE_URL + "icon.jpg";

    public final String PATH_TO_CARDS_IMAGES = GUI_PACKAGE_URL  + "cards/";

    public final String REVERSED_CARD_URL = PATH_TO_CARDS_IMAGES + "card_reverse.png";

    public final String INTRO_STAGE = GUI_PACKAGE_URL + "introScene.fxml";
}
