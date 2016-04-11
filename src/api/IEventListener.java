package api;

@Deprecated
/**
 * Interface for an event listener/handler
 *
 * @author Tom Wu
 */
public interface IEventListener extends ISerializable {

	public void handleEvent();

}
