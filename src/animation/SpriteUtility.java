package animation;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
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
	private VBox animationPropertiesBox;
	private Button saveButton;
	private TextField columnField;
	private TextField animationName;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
	public void start(Stage stage) throws Exception {
        pane = new BorderPane();
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
		rectangleList = new ArrayList<Rectangle>();
        hbox = new HBox();
        animationPropertiesBox = new VBox();

        spriteImage = new ImageView(initFileChooser());
        
        initImageViewHandlers(spriteImage);

        initRectangle();
        
        initAddButton();
        initTextFields();
        initPreviewButton();
        initSaveButton();

        animationPropertiesBox.getChildren().add(addButton);
        hbox.getChildren().addAll(spriteImage, animationPropertiesBox);
        pane.getChildren().addAll(hbox, rect);
        stage.show();

		
	}
    private void initPreviewButton() {
    	Button previewButton = new Button("Preview Animation");
    	previewButton.setMinWidth(100);
	}

	//add error handling; can't be empty
    private void initTextFields() {
    	columnField = new TextField("Columns");
    	animationName = new TextField("Animation Name");
    	animationPropertiesBox.getChildren().addAll(animationName, columnField);
    	
	}

//reordering functionality?
//need to write to properties file
	private void initSaveButton() {
		saveButton = new Button("Save Sprite Properties");
		saveButton.setMinWidth(100);
		animationPropertiesBox.getChildren().add(saveButton);
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
		List<String> xList = new ArrayList<String>();
		List<String> yList = new ArrayList<String>();
		List<String> widthList = new ArrayList<String>();
		List<String> heightList = new ArrayList<String>();
			@Override
			public void handle(ActionEvent arg0) {
				for (Rectangle rect: rectangleList){
					xList.add(Double.toString(rect.xProperty().get()));
					yList.add(Double.toString(rect.yProperty().get()));
					widthList.add(Double.toString(rect.widthProperty().get()));
					heightList.add(Double.toString(rect.heightProperty().get()));

				}
				System.out.println("x" + xList);
				System.out.println("y" + yList);
				System.out.println("width"+ widthList);
				System.out.println("height"+ heightList);

				System.out.println("Count" + rectangleList.size());
				System.out.println(columnField.getText());
				System.out.println(animationName.getText());
			}
            });


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
	protected void addRectangleToDisplay(Rectangle clone) {
		List<String> propertyList = Arrays.asList("x","y","width","height");
		for (String propertyName: propertyList){
			Label label = new Label(propertyName);
			TextField field = new TextField();
			field.setMinWidth(50);
			animationPropertiesBox.getChildren().addAll(label, field);
			try {
				Method method = clone.getClass().getMethod(propertyName+"Property");
				StringConverter<Number> converter = new NumberStringConverter();
				DoubleProperty rectProperty;
				try {
					rectProperty = (DoubleProperty) method.invoke(clone);
					Bindings.bindBidirectional(field.textProperty(),rectProperty, converter);

				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
//		TextField x = new TextField();
//		TextField y = new TextField();
//		TextField width = new TextField();
//		TextField height = new TextField();
//		x.setMinWidth(50);
//		y.setMinWidth(50);
//		width.setMinWidth(50);
//		height.setMinWidth(50);
//		rectangleListBox.getChildren().addAll(x,y,width,height);
//		Bindings.bindBidirectional(x.textProperty(),clone.xProperty(), converter);
//		Bindings.bindBidirectional(y.textProperty(),clone.yProperty(), converter);
//		Bindings.bindBidirectional(width.textProperty(),clone.widthProperty(), converter);
//		Bindings.bindBidirectional(height.textProperty(),clone.heightProperty(), converter);

	}

	protected Rectangle cloneRect(Rectangle rect) {
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

            }
        }

    };


}
