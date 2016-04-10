package model.events;

public class EventNotifier {

	private String message;
	
	public EventNotifier(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
