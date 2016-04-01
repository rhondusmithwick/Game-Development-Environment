package api;

public interface IActiveComponent extends IComponent {
    void update ();

    void update (ISystem system);
}
