package api;

import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;

public interface IView {

    IEntitySystem getEntitySystem ();

    ILevel getLevel ();

    void setScene (Scene scene);

}
