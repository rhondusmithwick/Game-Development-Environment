package events;

<<<<<<< HEAD
import api.IComponent;
import api.IEntitySystem;
=======
import api.ILevel;
>>>>>>> 4b37dbfbef7ed6e05fe66b5a93ac860c3b44c630
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/***
 * @author cyao42, ani
 *         Authors: Carolyn Yao, Anirudh Jonnavithula
 */

<<<<<<< HEAD
public class KeyTrigger extends Trigger{
	
	private String key; 
	
	public KeyTrigger(String key, IEntitySystem universe, InputSystem inputSystem) { 
		this.key = key; 
		addHandler(universe, inputSystem);
	}
	
	@Override
	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
		if (((KeyEvent) newValue).getCode() == KeyCode.getKeyCode(key)) { 
			setChanged();
			notifyObservers();
		}	
	}

	@Override
	public <T extends IComponent> void clearListener(IEntitySystem universe) {
	}

	@Override
	public <T extends IComponent> void addHandler(IEntitySystem universe, InputSystem inputSystem) {
		inputSystem.listenToKeyPress(this);
	}
=======
public class KeyTrigger extends Trigger {

    private String key;

    public KeyTrigger(String key) {
        this.key = key;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (((KeyEvent) newValue).getCode() == KeyCode.getKeyCode(key)) {
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void clearListener(ILevel universe, InputSystem inputSystem) {
        inputSystem.unListenToKeyPress(this);
    }

    @Override
    public void addHandler(ILevel universe, InputSystem inputSystem) {
        inputSystem.listenToKeyPress(this);
    }

    /**/
    public String toString() {
        return String.format("%s; %s", getClass().getSimpleName(), key);
    }
>>>>>>> 4b37dbfbef7ed6e05fe66b5a93ac860c3b44c630
}
