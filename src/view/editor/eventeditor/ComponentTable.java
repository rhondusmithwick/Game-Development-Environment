package view.editor.eventeditor;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import api.IComponent;
import api.ISerializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.Entity;

public class ComponentTable extends Table
{
	public ComponentTable(PropertyTableManager manager, String language)
	{
		super(manager, ResourceBundle.getBundle(language).getString("pickComponent"));	// TODO resource file

		// Add changeImage listener
		getTable().
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		try
        		{
        			manager.componentWasClicked((IComponent)observableValue.getValue().getData());
        		} catch (Exception e)
        		{
        			// TODO BAAAAAAD
        		}
        	}
        	);
   	}
	
	@Override
	public void fillEntries(Object dataHolder) 
	{
        List<Entity> entities = (List<Entity>) dataHolder;
        Set<IComponent> intersect = (Set) entities.get(0).getAllComponents();
//        entities.stream().forEach(entity -> {
//            intersect.retainAll((Set) entity.getAllComponents());
//        });
//        intersect.stream().forEach(component -> {
        entities.get(0).getAllComponents().stream().forEach(component -> {
            String[] splitClassName = component.getClass().toString().split("\\.");
            getEntries().add(new Entry(component, splitClassName[splitClassName.length - 1]));
            System.out.println(component);
        });

//		for (IComponent component: ((Entity)dataHolder).getAllComponents())
//		{
//			String[] splitClassName = component.getClass().toString().split("\\.");
//			getEntries().add(new Entry(component, splitClassName[splitClassName.length - 1]));
//		}
	}
}
