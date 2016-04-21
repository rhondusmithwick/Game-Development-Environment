package animation;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
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

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class SpriteUtility extends Application{
    private static final double DURATION_MIN = 100;
	private static final double DURATION_MAX = 3000;
	private static final double DURATION_DEFAULT = 1000;
	
	private ScrollPane pane;
    private Rectangle rect;
    private Map<String,Map> animationList;
	private List<Rectangle> rectangleList;
	private ImageView spriteImage;
	private HBox hbox;
	private VBox animationPropertiesBox;
	private TextField animationName;
	private File spriteSheet;
	private ImageView previewImage;
	private VBox buttonBox;
	private Group scrollGroup;
	private List<Double> widthList;
	private List<Double> heightList;
	private List<Double> yList;
	private List<Double> xList;
	private DoubleProperty rectinitX;
	private DoubleProperty rectinitY;
	private DoubleProperty rectX;
	private DoubleProperty rectY;
	private Slider durationSlider;
	
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
		animationList = new HashMap<String,Map>();
	    hbox = new HBox();
	    buttonBox = new VBox();
	    animationPropertiesBox = new VBox();
	    scrollGroup = new Group();

		initNewImage();
        initRectangle();
        initAddFrameButton();
        initAnimationGUIElements();
        initPreviewAnimationButton();
        initSaveAnimationButton();
        initNewAnimationButton();
        initSaveSpritePropertiesButton();
        initNewSpriteSheetButton();
        
        hbox.getChildren().addAll(spriteImage, buttonBox, animationPropertiesBox);
        scrollGroup.getChildren().addAll(hbox, rect);
        pane.setContent(scrollGroup);

	}

	private void initNewAnimationButton() {
		Button newAnimationButton = new Button("New Animation");
		buttonBox.getChildren().add(newAnimationButton);
		newAnimationButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				initAnimationGUIElements();
				rectangleList = new ArrayList<Rectangle>();

			}
		}
		);
	}

	private void initSaveSpritePropertiesButton() {
		Button saveSpriteSheetButton = new Button("Save Animations to File");
		buttonBox.getChildren().add(saveSpriteSheetButton);
		saveSpriteSheetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				initAnimationGUIElements();
				rectangleList = new ArrayList<Rectangle>();

			}
		}
		);
	}

	private void initNewImage() {
		spriteImage = new ImageView(initFileChooser());
        initImageViewHandlers(spriteImage);
	}
    private void initNewSpriteSheetButton() {
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

	private void initPreviewAnimationButton() {
    	Button previewButton = new Button("Preview Animation");
    	previewButton.setMinWidth(100);
		buttonBox.getChildren().add(previewButton);

    	previewButton.setOnAction(new EventHandler<ActionEvent>() {
    		
    			@Override
    			public void handle(ActionEvent arg0) {
    				populateRectanglePropertyLists();
    				initAnimationPreview();
    				}
    		});
    	}
	private void initAnimationPreview() {
		buttonBox.getChildren().remove(previewImage);
		previewImage = new ImageView( new Image(spriteSheet.toURI().toString()));
		Animation animation = new ComplexAnimation(
				previewImage, Duration.millis(durationSlider.getValue()), rectangleList.size(), xList, yList, widthList, heightList
				);
		animation.setCycleCount(Animation.INDEFINITE);

		animation.play();
		buttonBox.getChildren().add(previewImage);
	}
	//add error handling; can't be empty
    private void initAnimationGUIElements() {
    	buttonBox.getChildren().remove(previewImage);
    	for (Rectangle rectangle: rectangleList){
    		scrollGroup.getChildren().remove(rectangle);
    	}
    	animationPropertiesBox.getChildren().clear();
    	animationName = new TextField("Animation Name");
    	durationSlider = new Slider(DURATION_MIN,DURATION_MAX,DURATION_DEFAULT);
    	Label durationTextLabel = new Label("Duration");
    	Label durationValueLabel = new Label();
    	durationValueLabel.setText(String.format("%.2f", durationSlider.getValue()));
    	durationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
                        durationValueLabel.setText(String.format("%.2f", new_val));
                        initAnimationPreview();
                }
    	}
            );
    	animationPropertiesBox.getChildren().addAll(animationName, durationTextLabel,durationSlider, durationValueLabel);
    	
	}

//reordering functionality?
//need to write to properties file
	private void initSaveAnimationButton() {
		Button saveButton = new Button("Save Animation");
		saveButton.setMinWidth(100);
		buttonBox.getChildren().add(saveButton);
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				populateRectanglePropertyLists();
				HashMap<String,String> moveAnimationMap = new HashMap<String,String>();
				String name = animationName.getText();
				moveAnimationMap.put("Duration",String.format("%.2f",durationSlider.getValue()));
				moveAnimationMap.put("X",convertToString(xList));
				moveAnimationMap.put("Y",convertToString(yList));
				moveAnimationMap.put("Width",convertToString(widthList));
				moveAnimationMap.put("Height",convertToString(heightList));
				animationList.put(name, moveAnimationMap);
				System.out.println(animationList);
			}

            });

	}
	
	protected String convertToString(List<Double> list){
		StringBuilder str = new StringBuilder();		
		for (Double value: list){
			str.append(value.toString()+",");
		}
		return str.toString();
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
	private void initAddFrameButton() {
		Button addButton = new Button("Add Frame");
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
	    scrollGroup.getChildren().add(clone);

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
		spriteImage.setOnMouseDragged(RectangleDrawerHandler);
        spriteImage.setOnMousePressed(RectangleDrawerHandler);
        spriteImage.setOnMouseReleased(RectangleDrawerHandler);
	}

    private Image initFileChooser() {
		spriteSheet = Utilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"), "Choose a spritesheet");
		return new Image(spriteSheet.toURI().toString());
	}

    EventHandler<MouseEvent> RectangleDrawerHandler = new EventHandler<MouseEvent>() {

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
