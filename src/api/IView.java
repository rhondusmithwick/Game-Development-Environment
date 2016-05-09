package api;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public interface IView {
    Pane getPane ();

    void highlight (IEntity entity);

    void toggleHighlight (IEntity entity);

    IEntitySystem getEntitySystem ();

    ILevel getLevel ();

    void setScene (Scene scene);

    Scene getScene();

    void dehighlight (IEntity entity);
}
