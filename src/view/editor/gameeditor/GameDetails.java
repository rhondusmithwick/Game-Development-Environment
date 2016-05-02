package view.editor.gameeditor;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import utility.FilePathUtility;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.enums.Indexes;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;
import view.utilities.TextFieldFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static utility.FilePathUtility.relativize;

/**
 * @author calinelson
 */
public class GameDetails {

    private final ResourceBundle myResources;
    private HBox nameBox, descriptionBox, iconBox, levelBox;
    private TextField name, desc, level;
    private String iconPath;
    private ImageView icon;

    public GameDetails (String language) {
        myResources = ResourceBundle.getBundle(language);
        createDetails();

    }

    private void createDetails () {
        nameBox = createTextEntry("gameName");
        name = (TextField) nameBox.getChildren().get(1);
        descriptionBox = createTextEntry("gameDescription");
        desc = (TextField) descriptionBox.getChildren().get(1);
        levelBox = createTextEntry("firstLevel");
        level = (TextField) levelBox.getChildren().get(1);

        showIcon();

    }

    private HBox createTextEntry (String name) {
        HBox container = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
        Label title = new Label(myResources.getString(name));
        title.setMinWidth(GUISize.LABEL_MIN_WIDTH.getSize());
        TextField tArea = TextFieldFactory.makeTextArea(myResources.getString(name));
        container.getChildren().addAll(title, tArea);
        return container;
    }


    private HBox showIcon () {
        iconBox = new HBox(GUISize.GAME_EDITOR_HBOX_PADDING.getSize());
        iconBox.setAlignment(Pos.CENTER_LEFT);
        Label iconTitle = new Label(myResources.getString("gameIcon"));
        icon = new ImageView();
        setIconPicture(new File(DefaultStrings.DEFAULT_ICON.getDefault()));
        iconBox.getChildren().addAll(iconTitle, icon, ButtonFactory.makeButton(myResources.getString("chooseIcon"), e -> updateIcon()));
        return iconBox;
    }

    private void setIconPicture (File file) {
        //iconPath = relativize(file.toURI().toString());
    	//iconPath = file.getPath();
        //setImage();
    }

    private void setImage () {
        icon.setImage(new Image(iconPath));
        icon.setFitHeight(GUISize.ICON_SIZE.getSize());
        icon.setFitWidth(GUISize.ICON_SIZE.getSize());
    }

    private void updateIcon () {
        File file = FileUtilities.promptAndGetFile(FileUtilities.getImageFilters(),
                myResources.getString("chooseIcon"), DefaultStrings.GUI_IMAGES.getDefault());
        if (file != null) {
            setIconPicture(new File(relativize(file.getPath())));
        }
    }

    public void setDetails (List<String> list) {
        name.setText(list.get(Indexes.GAME_NAME.getIndex()));
        desc.setText(list.get(Indexes.GAME_DESC.getIndex()));
        iconPath = list.get(Indexes.GAME_ICON.getIndex());
        level.setText(list.get(Indexes.GAME_FIRST_LEVEL.getIndex()));
        setImage();
    }


    public List<Node> getElements () {
        return Arrays.asList(nameBox, descriptionBox, iconBox, levelBox);
    }

    public List<String> getGameDetails () {
        String nameText = name.getText();
        if (nameText.isEmpty()) {
            nameText = DefaultStrings.DEF_NAME.getDefault();
        }
        return Arrays.asList(nameText, desc.getText(), iconPath, level.getText());
    }
}
