package api;

import java.util.Collection;


/** Reference: http://stackoverflow.com/questions/937302/simple-java-message-dispatching-system */
public interface IEventSystem extends ISystem {
    /** Add a listener to an event class **/
    <L> void listen (Class<IEvent<L>> eventClass, L listener);

    /** Stop sending an event class to a given listener **/
    <L> void mute (Class<IEvent<L>> eventClass, L listener);

    /** Gets listeners for a given event class **/
    <L> Collection<L> listenersOf (Class<IEvent<L>> eventClass);
}
