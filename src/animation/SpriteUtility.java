package animation;

import java.io.File;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Utilities;

public class SpriteUtility extends Application{
    BorderPane pane;
    Rectangle rect;
    SimpleDoubleProperty rectinitX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectinitY = new SimpleDoubleProperty();
    SimpleDoubleProperty rectX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectY = new SimpleDoubleProperty();
	private Image initFileChooser() {
		File spriteSheet = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"), "Choose a spritesheet");
		return new Image(spriteSheet.toURI().toString());
	}

    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                rect.setX(mouseEvent.getX());
                rect.setY(mouseEvent.getY());
                rectinitX.set(mouseEvent.getX());
                rectinitY.set(mouseEvent.getY());
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                rectX.set(mouseEvent.getX());
                rectY.set(mouseEvent.getY());
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                // Clone the rectangle
                Rectangle r = getNewRectangle();
                r.setX(rect.getX());
                r.setY(rect.getY());
                r.setWidth(rect.getWidth());
                r.setHeight(rect.getHeight());
                pane.getChildren().add(r);

                // Hide the rectangle
                rectX.set(0);
                rectY.set(0);
            }
        }

		private Rectangle getNewRectangle() {
			// TODO Auto-generated method stub
			return null;
		}
    };
	@Override
	public void start(Stage stage) throws Exception {
        pane = new BorderPane();
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        ImageView spriteImage = new ImageView(initFileChooser());
        
        spriteImage.setOnMouseDragged(mouseHandler);
        spriteImage.setOnMousePressed(mouseHandler);
        spriteImage.setOnMouseReleased(mouseHandler);

//        rect = getNewRectangle();
//        rect.widthProperty().bind(rectX.subtract(rectinitX));
//        rect.heightProperty().bind(rectY.subtract(rectinitY));
        pane.getChildren().add(spriteImage);
        stage.show();

		
	}

	private Rectangle getNewRectangle() {
		Rectangle r = new Rectangle();
		return r;
	}
}
