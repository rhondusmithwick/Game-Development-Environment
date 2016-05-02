package model.component.hud;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.List;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

public class HUD implements IComponent {
    private static final String DEFAULT = "Shape:Rectangle;Width:200;Height:100;Color:10,10,10,1";
    private final SingleProperty<String> singleProperty = new SingleProperty<>("HUD", DEFAULT);

    public HUD () {
        this(DEFAULT);
    }

    public HUD (String data) {
        setHUD(data);
    }

    public SimpleObjectProperty<String> HUDProperty () {
        return singleProperty.property1();
    }

    public String getHUD () {
        return HUDProperty().get();
    }

    public void setHUD (String hud) {
        HUDProperty().set(hud);
    }
    
    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return Arrays.asList(HUDProperty());
    }


    @Override
    public void update () {
        setHUD(getHUD());
    }


}
