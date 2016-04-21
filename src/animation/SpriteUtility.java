package animation;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import view.Utilities;

public class SpriteUtility extends Application{
    ScrollPane pane;
    Rectangle rect;


	private Button addButton;
	protected List<Rectangle> rectangleList;
	private ImageView spriteImage;
	private HBox hbox;
	private VBox animationPropertiesBox;
	private Button saveButton;
	private TextField animationName;
	private File spriteSheet;
	private ImageView previewImage;
	private VBox buttonBox;
	private Group scrollGroup;
	private TextField speedField;
	private List<Double> widthList;
	private List<Double> heightList;
	private List<Double> yList;
	private List<Double> xList;
	private DoubleProperty rectinitX;
	private DoubleProperty rectinitY;
	private DoubleProperty rectX;
	private DoubleProperty rectY;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
	public void start(Stage stage) throws Exception {
        pane = new ScrollPane();
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);


        
        initializeGui();
        stage.show();

		
	}

	private void initializeGui() {		
		rectangleList = new ArrayList<Rectangle>();
	    hbox = new HBox();
	    buttonBox = new VBox();
	    animationPropertiesBox = new VBox();
	    scrollGroup = new Group();

		initNewImage();
        initRectangle();
        initAddButton();
        initTextFields();
        initPreviewButton();
        initSaveButton();
        initNewSpriteButton();
        hbox.getChildren().addAll(spriteImage, buttonBox, animationPropertiesBox);
        scrollGroup.getChildren().addAll(hbox, rect);
        pane.setContent(scrollGroup);

	}

	private void initNewImage() {
		spriteImage = new ImageView(initFileChooser());
        initImageViewHandlers(spriteImage);
	}
    private void initNewSpriteButton() {
    	Button newSpriteButton = new Button("New Sprite");
    	buttonBox.getChildren().add(newSpriteButton);
    	newSpriteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				initializeGui();
			}
    	}
    	);
	}

	private void initPreviewButton() {
    	Button previewButton = new Button("Preview Animation");
    	previewButton.setMinWidth(100);
		buttonBox.getChildren().add(previewButton);

    	previewButton.setOnAction(new EventHandler<ActionEvent>() {
    		
    			@Override
    			public void handle(ActionEvent arg0) {
    				populateRectanglePropertyLists();
    				//needs to throw parseDouble errors
    				if(speedField.getText().equals("")){
    					System.out.println("Speed Field is Empty");
    				}
    				else{
    				buttonBox.getChildren().remove(previewImage);
    				previewImage = new ImageView( new Image(spriteSheet.toURI().toString()));
    				Animation animation = new ComplexAnimation(
    						previewImage, Duration.millis(Double.parseDouble(speedField.getText())), rectangleList.size(), xList, yList, widthList, heightList
    						);
    				animation.setCycleCount(Animation.INDEFINITE);

    				animation.play();
    				buttonBox.getChildren().add(previewImage);
    				}

    			}
    			
    			
	});
    	}

	//add error handling; can't be empty
    private void initTextFields() {
    	animationName = new TextField("Animation Name");
    	speedField = new TextField("Speed");
    	animationPropertiesBox.getChildren().addAll(animationName, speedField);
    	
	}

//reordering functionality?
//need to write to properties file
	private void initSaveButton() {
		saveButton = new Button("Save Sprite Properties");
		saveButton.setMinWidth(100);
		buttonBox.getChildren().add(saveButton);
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				populateRectanglePropertyLists();
				System.out.println("x" + xList);
				System.out.println("y" + yList);
				System.out.println("width"+ widthList);
				System.out.println("height"+ heightList);

				System.out.println("Count" + rectangleList.size());
				System.out.println(animationName.getText());
			}


            });


	}
	
	private void populateRectanglePropertyLists() {
		xList = new ArrayList<Double>();
		yList = new ArrayList<Double>();
		widthList = new ArrayList<Double>();
		heightList = new ArrayList<Double>();
		for (Rectangle rect: rectangleList){
			xList.add(rect.xProperty().get());
			yList.add(rect.yProperty().get());
			widthList.add(rect.widthProperty().get());
			heightList.add(rect.heightProperty().get());

		}
	}
	private void initAddButton() {
		addButton = new Button("Add Frame");
        addButton.setMinWidth(100);
        buttonBox.getChildren().add(addButton);

        addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Rectangle clone = cloneRect(rect);
				rectangleList.add(clone);
				addRectangleToDisplay(clone);
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

	}

	protected Rectangle cloneRect(Rectangle rect) {
	    Rectangle r = new Rectangle();
	    r.setX(rect.getX());
	    r.setY(rect.getY());
	    r.setWidth(rect.getWidth());
	    r.setHeight(rect.getHeight());
	    r.setFill(Color.TRANSPARENT);
	    r.setStroke(Color.RED);
	    scrollGroup.getChildren().add(r);
	    return r;
	    }

	private void initRectangle() {
		rect = new Rectangle(); 
		rect.widthProperty().unbind();
		rect.heightProperty().unbind();
		rectinitX = new SimpleDoubleProperty();
	    rectinitY = new SimpleDoubleProperty();
	    rectX = new SimpleDoubleProperty();
	    rectY = new SimpleDoubleProperty();

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
		spriteSheet = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"), "Choose a spritesheet");
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
