package view.editor;

import api.IEditor;
import api.IEntity;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.entity.Level;
import view.enums.GUISize;

import java.util.ArrayList;
import java.util.List;

public abstract class Editor implements IEditor {
    private final List<IEntity> entityList;

    public Editor () {
        entityList = new ArrayList<>();
    }

    public List<IEntity> getEntities () {
        return entityList;

    }

    public void addEntity (IEntity entity) {
        entityList.add(entity);
    }

    public void createEntity () {
        Level level = new Level();
        IEntity entity = level.createEntity();
        addEntity(entity);
    }

    public abstract void updateEditor ();

    protected Text saveMessage (String message) {
        Text text = new Text();
        text.setFont(new Font(GUISize.SAVE_MESSAGE_FONT.getSize()));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText(message);
        return text;
    }
}
