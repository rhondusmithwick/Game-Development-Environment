package guiObjects;

import java.util.ResourceBundle;

import api.IEditor;
import api.IEntity;
import javafx.scene.control.Button;
import view.editor.AnimationEditor;
import view.editor.EditorFactory;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.PopUp;
/**
 * Class that makes button to open animation editor
 * @author calinelson
 *
 */
public class AnimationButton extends GuiObject{

	private Button editButton;
	private ResourceBundle myComponentNames;
	/**
	 * create new animation button class
	 * @param name title of button
	 * @param resourceBundle resourcebundle for gui objects
	 * @param language display language
	 * @param entity entity to set animation for
	 */
	public AnimationButton(String name, String resourceBundle, String language, IEntity entity) {
		super(name, resourceBundle);
		this.myComponentNames= ResourceBundle.getBundle(language + DefaultStrings.COMPONENTS.getDefault());
		editButton = ButtonFactory.makeButton(myComponentNames.getString(name), e->makeAnimationEditor(entity, language));
		
		
	}

	private void makeAnimationEditor(IEntity entity, String language) {
		IEditor a =  new EditorFactory().createEditor(AnimationEditor.class.getName(), entity, language);
		a.populateLayout();
		new PopUp(GUISize.ANIMATION_HEIGHT.getSize(), GUISize.ANIMATION_WIDTH.getSize()).show(a.getPane());
	}

	@Override
	public Object getCurrentValue() {
		return null;
	}

	@Override
	public Object getGuiNode() {
		return editButton;
	}

}
