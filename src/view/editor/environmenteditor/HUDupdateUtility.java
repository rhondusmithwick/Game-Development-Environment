package view.editor.environmenteditor;

import java.util.ResourceBundle;

import api.IEntity;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import model.component.character.Health;
import model.component.character.Lives;
import model.component.character.Score;
import model.component.hud.HUD;
import model.component.movement.Position;
import view.enums.GUISize;



/**
 * @author Ben Zhang (Bruna Liborio pulled out of the view class)
 *
 */
public class HUDupdateUtility {

	public static StackPane updateHUD(IEntity e, ResourceBundle myResources) {
			String hud = e.getComponent(HUD.class).getHUD();
			@SuppressWarnings("unused")
			String shape = "", color = "";
			double width = 0, height = 0;
			for (String str : hud.split(";")) {
				String[] strip = str.split(":");
				String key = strip[0];
				String val = strip[1];
				if (key.equals(myResources.getString("shape"))) {
					shape = val;
				}
				if (key.equals(myResources.getString("width"))) {
					width = Double.parseDouble(val);
				}
				if (key.equals(myResources.getString("height"))) {
					height = Double.parseDouble(val);
				}
				if (key.equals(myResources.getString("color"))) {
					color = val;
				}
			}
			Rectangle s = new Rectangle(width, height);
			// Shape shape = (Shape) Reflection.createInstance(shape, width,
			// height);
			String[] strip = color.split(",");
			s.setFill(Color.rgb(Integer.parseInt(strip[0]), Integer.parseInt(strip[1]), Integer.parseInt(strip[2])));
			s.setOpacity(Double.parseDouble(strip[3]));
			double x = e.getComponent(Position.class).getX();
			double y = e.getComponent(Position.class).getY();
			double padding = GUISize.HUD_PADDING.getSize();
			String text = "";
			if (e.hasComponent(Score.class)) {
				double score = e.getComponent(Score.class).getScore();
				text += Score.class.getSimpleName() + ": " + Double.toString(score) + "\n";
			}
			if (e.hasComponent(Lives.class)) {
				int lives = e.getComponent(Lives.class).getLives();
				text += Lives.class.getSimpleName() + ": " + Integer.toString(lives) + "\n";
			}
			if (e.hasComponent(Health.class)) {
				double health = e.getComponent(Health.class).getHealth();
				text += Health.class.getSimpleName() + ": " + Double.toString(health) + "\n";
			}
			StackPane stack = new StackPane();
			stack.setLayoutX(x);
			stack.setLayoutY(y - padding);
			Text t = new Text(text);
			t.setFill(Color.WHITE);
			t.setBoundsType(TextBoundsType.VISUAL);
			stack.getChildren().addAll(s, t);
			return stack;
		}

}
