package model.component;

import api.ISystem;

public interface IActiveComponent extends IComponent {
    void act(ISystem system);
}
