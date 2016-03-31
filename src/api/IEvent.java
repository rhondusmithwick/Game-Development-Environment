package api;

public interface IEvent {
    IEventListener getEventListener ();

    IEventHandler getEventHandler ();
}
