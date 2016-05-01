package view.editor.eventeditor.tables;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import api.IComponent;
import api.IEntity;
import api.ISerializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.Entity;

/**
 * Component Table. Lists certain Components from a given entity.
 * @author Alankmc
 *
 */
public class ComponentTable extends Table
{
	public ComponentTable(PropertyTableManager manager, String language) throws NoSuchMethodException, SecurityException
	{
		// Passes the manager's pickComponent method.
		super(manager, ResourceBundle.getBundle(language).getString("pickComponent"), 
				manager.getClass().getMethod("componentWasClicked", IComponent.class),
				IComponent.class);	
   	}
	
	@Override
	public void fillEntries(Object dataHolder) 
	{
//        List<Entity> entities = (List<Entity>) dataHolder;
//        entities.stream().forEach(entity -> {
//            System.out.println(entity.getName());
//            entity.getAllComponents().stream().forEach(component -> {
//                String[] splitClassName = component.getClass().toString().split("\\.");
//                getEntries().add(new Entry(component, splitClassName[splitClassName.length - 1]));
//            });
//        });
//        Set<IComponent> intersect = (Set) entities.get(0).getAllComponents();
//        entities.stream().forEach(entity -> {
//            intersect.retainAll((Set) entity.getAllComponents());
//        });
//        intersect.stream().forEach(component -> {


//        entities.get(0).getAllComponents().stream().forEach(component -> {
//            String[] splitClassName = component.getClass().toString().split("\\.");
//            getEntries().add(new Entry(component, splitClassName[splitClassName.length - 1]));
//            System.out.println(component);
//        });
        IEntity entity = (Entity) dataHolder;
        entity.getAllComponents().stream().forEach(component -> {
            String[] splitClassName = component.getClass().toString().split("\\.");
            getEntries().add(new Entry(component, splitClassName[splitClassName.length - 1]));
        });
//		for (IComponent component: ((Entity)dataHolder).getAllComponents())
//		{
//			String[] splitClassName = component.getClass().toString().split("\\.");
//			getEntries().add(new Entry(component, splitClassName[splitClassName.length - 1]));
//		}
	}
}
