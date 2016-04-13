package games;

import api.IEntity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.component.movement.Position;

public class ACGameXChangeListener implements ChangeListener{

	private IEntity character;
	
	public ACGameXChangeListener(IEntity character) {
		this.character = character;
	}
//	
//	public void listen(IEntity character) { 
//		character.getComponent(Position.class).getX().addListener(this);
//	}
//	
//	@Override
//	public void changed(ObservableValue<? extends Double> arg0, Double arg1,
//			Double arg2) {
//		if (arg0.getValue() > 500) { 
//			System.out.println("out of bounds");
//		}
//		
//	}
	
}
