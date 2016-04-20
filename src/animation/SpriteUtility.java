package animation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import view.Utilities;

public class SpriteUtility extends Application{
    BorderPane pane;
    Rectangle rect;
    SimpleDoubleProperty rectinitX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectinitY = new SimpleDoubleProperty();
    SimpleDoubleProperty rectX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectY = new SimpleDoubleProperty();
	private Button addButton;
	protected ArrayList<Rectangle> rectangleList;
	private ImageView spriteImage;
	private HBox hbox;
	private VBox rectangleListBox;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
	public void start(Stage stage) throws Exception {
        pane = new BorderPane();
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
		rectangleList = new ArrayList<Rectangle>();

        spriteImage = new ImageView(initFileChooser());
        
        initImageViewHandlers(spriteImage);

        initRectangle();
        
        initAddButton();
        
        
        hbox = new HBox();
        rectangleListBox = new VBox();
        rectangleListBox.getChildren().add(addButton);
        hbox.getChildren().addAll(spriteImage, rectangleListBox);
        pane.getChildren().addAll(hbox, rect);
        stage.show();

		
	}

	private void initAddButton() {
		addButton = new Button("Add Frame");
        addButton.setMinWidth(100);
        addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Rectangle clone = cloneRect(rect);
				rectangleList.add(clone);
				addRectangleToDisplay(clone);
				System.out.println(rectangleList);
			}
            });
	}
//Todo: Refactor this class
	protected void addRectangleToDisplay(Rectangle clone) {
		TextField x = new TextField();
		TextField y = new TextField();
		TextField width = new TextField();
		TextField height = new TextField();
		x.setMinWidth(50);
		y.setMinWidth(50);
		width.setMinWidth(50);
		height.setMinWidth(50);
		rectangleListBox.getChildren().addAll(x,y,width,height);
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(x.textProperty(),clone.xProperty(), converter);
		Bindings.bindBidirectional(y.textProperty(),clone.yProperty(), converter);
		Bindings.bindBidirectional(width.textProperty(),clone.widthProperty(), converter);
		Bindings.bindBidirectional(height.textProperty(),clone.heightProperty(), converter);

	}

	protected Rectangle cloneRect(Rectangle rect2) {
	    Rectangle r = new Rectangle();
	    r.setX(rect.getX());
	    r.setY(rect.getY());
	    r.setWidth(rect.getWidth());
	    r.setHeight(rect.getHeight());
	    r.setFill(Color.TRANSPARENT);
	    r.setStroke(Color.RED);
	    pane.getChildren().add(r);
	    return r;
	    }

	private void initRectangle() {
		rect = new Rectangle();
        rect.widthProperty().bind(rectX.subtract(rectinitX));
        rect.heightProperty().bind(rectY.subtract(rectinitY));
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);
	}

	private void initImageViewHandlers(ImageView spriteImage) {
		spriteImage.setOnMouseDragged(RectangleMouseHandler);
        spriteImage.setOnMousePressed(RectangleMouseHandler);
        spriteImage.setOnMouseReleased(RectangleMouseHandler);
	}

    private Image initFileChooser() {
		File spriteSheet = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"), "Choose a spritesheet");
		return new Image(spriteSheet.toURI().toString());
	}

    EventHandler<MouseEvent> RectangleMouseHandler = new EventHandler<MouseEvent>() {

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
////                Rectangle r = getNewRectangle();
////                r.setX(rect.getX());
////                r.setY(rect.getY());
////                r.setWidth(rect.getWidth());
////                r.setHeight(rect.getHeight());
////                pane.getChildren().add(r);
//
//                // Hide the rectangle
//                rectX.set(0);
//                rectY.set(0);
            }
        }

    };

//	private Rectangle getNewRectangle() {
//		Rectangle r = new Rectangle();
//		return r;
//	}
}
