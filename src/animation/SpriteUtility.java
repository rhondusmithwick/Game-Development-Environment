package animation;

import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import view.Dragger;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class SpriteUtility {
    private static final double DURATION_MIN = 100;
    private static final double DURATION_MAX = 3000;
    private static final double DURATION_DEFAULT = 1000;
    private static final String SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,50,0.8), 10, 0, 0, 0)";
    private static final String NO_SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0)";
    private BorderPane mainPane;
    private VBox animationPropertiesBox;
    private VBox buttonBox;

    private Rectangle rect;

    private Map<String, Map> animationList;
    private List<Rectangle> rectangleList;

    private File spriteSheet;
    private ImageView spriteImage;
    private ImageView previewImage;

    private TextField animationName;

    private List<Double> widthList;
    private List<Double> heightList;
    private List<Double> yList;
    private List<Double> xList;
    private DoubleProperty rectinitX;
    private DoubleProperty rectinitY;
    private DoubleProperty rectX;
    private DoubleProperty rectY;

    private Slider durationSlider;

    private Group spriteGroup;
	private Rectangle selectedRectangle;

    private final SimpleObjectProperty<Boolean> changeColorProperty = new SimpleObjectProperty<>(this, "ChangeColor", false);
	private Button activateTransparencyButton;
	private Canvas canvas;
	private Scene scene;

    public void init(Stage stage, Dimension2D dimensions) {
        mainPane = new BorderPane();
        scene = new Scene(mainPane, dimensions.getWidth(), dimensions.getHeight());
        stage.setScene(scene);
        initializeGui();
        selectedRectangle = null;


    }

    private void initializeGui() {
        rectangleList = new ArrayList<>();
        animationList = new HashMap<>();

        buttonBox = new VBox();
        animationPropertiesBox = new VBox();
        spriteGroup = new Group();
        ScrollPane spriteScroll = new ScrollPane(spriteGroup);

        initNewImage();
        initRectangleDrawer();
        initAnimationProperties();
        initButtons();

        mainPane.setCenter(spriteScroll);
        mainPane.setRight(buttonBox);
        mainPane.setLeft(animationPropertiesBox);
    }

    private void initButtons() {
        addButton(UtilityUtilities.makeButton("Save Animations to File", e -> reInitialize()), buttonBox);
        addButton(UtilityUtilities.makeButton("New Animation", e -> reInitialize()), buttonBox);
        addButton(UtilityUtilities.makeButton("New Sprite", e -> initializeGui()), buttonBox);
        addButton(UtilityUtilities.makeButton("Preview Animation", e -> animationPreview()), buttonBox);
        addButton(UtilityUtilities.makeButton("Save Animation", e -> saveAnimation()), buttonBox);
        addButton(UtilityUtilities.makeButton("Add Frame", e -> addFrame()), buttonBox);
        addButton(UtilityUtilities.makeButton("Delete Frame",e -> deleteFrame(selectedRectangle)), buttonBox);
        activateTransparencyButton  = UtilityUtilities.makeButton("Activate Transparency", e-> makeTransparent());
        addButton(activateTransparencyButton, buttonBox);
    }

    private void makeTransparent() {
    	if (changeColorProperty.get()){
    		activateTransparencyButton.setText("Activate Transparency");
    		changeColorProperty.set(false);
    		scene.setCursor(Cursor.DEFAULT);

    	}else{
    		activateTransparencyButton.setText("Deactivate Transparency");
    		changeColorProperty.set(true);
			scene.setCursor(Cursor.CROSSHAIR);


    	}
	}

	private void deleteFrame(Rectangle frameToDelete) {
    	if(frameToDelete!= null){
    		for (Rectangle existingRect: rectangleList){
    			if (existingRect == frameToDelete){
    				if (selectedRectangle == frameToDelete){ selectedRectangle = null;}
    				spriteGroup.getChildren().remove(frameToDelete);
    				rectangleList.remove(existingRect);
    				initAnimationProperties();

    				break;
    			}
    		}
    	}
    }
    /*
     * Initialize all animation gui elements (i.e. left side of pane)
     */
	private void initAnimationProperties() {
        buttonBox.getChildren().remove(previewImage);
        animationPropertiesBox.getChildren().clear();
		initAnimationNameAndDurationFields();
		for (Rectangle rectangleToAdd: rectangleList){
			displayRectangleListProperties(rectangleToAdd);
			
		}
	}

	private void addButton(Button button, VBox box) {
        button.setMaxWidth(Double.MAX_VALUE);
        box.getChildren().add(button);
    }



	/*
	 * Initialize sprite sheet gui properties like animation name and duration
	 */
	private void initAnimationNameAndDurationFields() {
		animationName = new TextField("Animation Name");
        durationSlider = new Slider(DURATION_MIN, DURATION_MAX, DURATION_DEFAULT);
        Label durationTextLabel = new Label("Duration");
        Label durationValueLabel = new Label(String.format("%.2f", durationSlider.getValue()));
        durationSlider = UtilityUtilities.makeSlider((ov, old_val, new_val) -> {
            durationValueLabel.setText(String.format("%.2f", new_val));
            initAnimationPreview();
        }, DURATION_MIN, DURATION_MAX, DURATION_DEFAULT);
        animationPropertiesBox.getChildren().addAll(animationName, durationTextLabel, durationSlider,
                durationValueLabel);
	}

    private void reInitialize() {
        spriteGroup.getChildren().removeAll(rectangleList);
        rectangleList = new ArrayList<>();
        animationList = new TreeMap<String,Map>();
        initAnimationProperties();

    }

    private void initNewImage() {
        spriteImage = new ImageView(initFileChooser());
        spriteGroup.getChildren().add(spriteImage);
        canvas = new Canvas(spriteImage.getBoundsInParent().getWidth(),spriteImage.getBoundsInParent().getHeight());
        canvas.layoutXProperty().set(spriteImage.getLayoutX());
        canvas.layoutYProperty().set(spriteImage.getLayoutY());

        initCanvasHandlers(canvas);
        spriteGroup.getChildren().add(canvas);

    }

    private void initCanvasHandlers(Canvas canvas2) {
        canvas2.setOnMouseDragged(this::mouseDragged);
        canvas2.setOnMousePressed(this::mousePressed);
        canvas2.setOnMouseReleased(this::mouseReleased);
        canvas2.setOnMouseClicked(this::mouseClicked);
	}

	private void animationPreview() {
        populateRectanglePropertyLists();
        initAnimationPreview();
    }

    private void initAnimationPreview() {
        buttonBox.getChildren().remove(previewImage);
        previewImage = new ImageView(new Image(spriteSheet.toURI().toString()));
        Animation animation = new ComplexAnimation(previewImage, Duration.millis(durationSlider.getValue()),
                rectangleList.size(), xList, yList, widthList, heightList);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        buttonBox.getChildren().add(previewImage);
    }

    private void saveAnimation() {
        populateRectanglePropertyLists();
        TreeMap<String, String> moveAnimationMap = new TreeMap<>();
        String name = animationName.getText();
        moveAnimationMap.put("Duration", String.format("%.2f", durationSlider.getValue()));
        moveAnimationMap.put("X", convertListToString(xList));
        moveAnimationMap.put("Y", convertListToString(yList));
        moveAnimationMap.put("Width", convertListToString(widthList));
        moveAnimationMap.put("Height", convertListToString(heightList));
        animationList.put(name, moveAnimationMap);
        System.out.println(animationList);
    }

    private String convertListToString(List<Double> list) {
        StringBuilder str = new StringBuilder();
        for (Double value : list) {
            str.append(value.toString()).append(",");
        }
        return str.toString();
    }
    /*
     * 
     */
    private void populateRectanglePropertyLists() {
        xList = new ArrayList<>();
        yList = new ArrayList<>();
        widthList = new ArrayList<>();
        heightList = new ArrayList<>();
        for (Rectangle rect : rectangleList) {
            xList.add(rect.xProperty().get());
            yList.add(rect.yProperty().get());
            widthList.add(rect.widthProperty().get());
            heightList.add(rect.heightProperty().get());
        }
    }

    private void addFrame() {
        Rectangle clone = cloneRect(rect);
        rectangleList.add(clone);
        addRectangleToDisplay(clone);
    }

    private void addRectangleToDisplay(Rectangle clone) {
        spriteGroup.getChildren().add(clone);
        displayRectangleListProperties(clone);
    }
    /*
     * Puts properties of all frames on GUI i.e. x, y, width, height; Makes them editable
     */
	private void displayRectangleListProperties(Rectangle clone) {
        List<String> propertyList = Arrays.asList("x", "y", "width", "height");

		for (String propertyName : propertyList) {
            Label label = new Label(propertyName);
            TextField field = new TextField();
            field.setMinWidth(50);
            animationPropertiesBox.getChildren().addAll(label, field);
            try {
                Method method = clone.getClass().getMethod(propertyName + "Property");
                StringConverter<Number> converter = new NumberStringConverter();
                DoubleProperty rectProperty;
                rectProperty = (DoubleProperty) method.invoke(clone);
                Bindings.bindBidirectional(field.textProperty(), rectProperty, converter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

    private Rectangle cloneRect(Rectangle rect) {
        Rectangle r = new Rectangle();
        r.setX(rect.getX());
        r.setY(rect.getY());
        r.setWidth(rect.getWidth());
        r.setHeight(rect.getHeight());
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.RED);
        Dragger.makeDraggable(r);
        makeSelectable(r);
        return r;
    }

	private void makeSelectable(Rectangle r) {
		r.setOnMouseClicked((e->{
        	addSelectEffect(r);
        	selectedRectangle = r;
        	for (Rectangle rect: rectangleList){
        		if (rect != selectedRectangle){
        		removeSelectEffect(rect);
        		}
        	}
        }));
	}


    public void addSelectEffect(Rectangle img){
    	img.setStyle(SELECT_EFFECT);
    	}
    public void removeSelectEffect(Rectangle imageView) {
    	imageView.setStyle(NO_SELECT_EFFECT);
    	}
    /*
     * Initializes initial rectangle drawer
     */
	private Rectangle initRectangleDrawer() {
        spriteGroup.getChildren().remove(rect);
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

        Dragger.makeDraggable(rect);
        spriteGroup.getChildren().add(rect);
        return rect;
    }

    private Image initFileChooser() {
        spriteSheet = UtilityUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"),
                "Choose a spritesheet");
        return new Image(spriteSheet.toURI().toString());
    }


    private void mouseReleased(MouseEvent event) {
    	if (changeColorProperty.get()){
			scene.setCursor(Cursor.CROSSHAIR);
    	}else{
    		scene.setCursor(Cursor.DEFAULT);
    		canvas.setCursor(Cursor.DEFAULT);
    	}
    	
    }

    private void mousePressed(MouseEvent event) {
    	if(!changeColorProperty.get()){
	        canvas.setCursor(Cursor.CLOSED_HAND);
	    	if (event.getButton() == MouseButton.PRIMARY) {
	            rect = initRectangleDrawer();
	            rect.setX(event.getX());
	            rect.setY(event.getY());
	            rectinitX.set(event.getX());
	            rectinitY.set(event.getY());
	        } else if (event.getButton() == MouseButton.SECONDARY) {
	            rect.setX(event.getX());
	            rect.setY(event.getY());
	        }
    	}
    }

    private void mouseDragged(MouseEvent event) {
        rectX.set(event.getX());
        rectY.set(event.getY());
    }
    private void mouseClicked(MouseEvent event){
            if (changeColorProperty.get()) {
                double x = event.getX();
                double y = event.getY();
                ColorChanger colorChanger = new ColorChanger(spriteImage.getImage(), x, y, Color.TRANSPARENT);
                colorChanger.change();
                spriteImage.setImage(colorChanger.getImage());

            }
        
    }

}
