package events;

import javafx.beans.value.ObservableValue;
import api.ILevel;

public class TimeTrigger extends Trigger{

	private double time;
	
	public TimeTrigger(double time) {
		this.time = time;
	}
	
	@Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		Double oldValue = (Double) arg1;
		Double newValue = (Double) arg2;
		if(oldValue<time && newValue>=time) {
			setChanged();
			notifyObservers();
		}
	}

	@Override
	@Deprecated
	public void clearListener(ILevel universe, InputSystem inputSystem) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void clearListener(ILevel universe) {
		universe.getEventSystem().unListenToTimer(this);
	}

	@Override
	@Deprecated
	public void addHandler(ILevel universe, InputSystem inputSystem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addHandler(ILevel universe) {
		universe.getEventSystem().listenToTimer(this);
	}

}
