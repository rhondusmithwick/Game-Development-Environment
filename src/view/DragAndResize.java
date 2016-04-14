package view;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.component.movement.Position;
import model.component.visual.ImagePath;

public class DragAndResize {
        
    private double margin = 8;

	private boolean resizing;
	private boolean dragging;
	private double parentMinX;
	private double parentMinY;
	private double parentHeight;
	private double parentWidth;
	private double clickX;
	private double clickY;
	private double minW;
	private double minH;
	private Position position;
    private final ImageView image;
    private final ImagePath component;
    
    private DragAndResize(ImagePath component, Position aPos) {
    	this.component=component;
        image = component.getImageView();
        position = aPos;
        minW = image.minWidth(image.getFitHeight());
        minH = image.minHeight(image.getFitWidth());
    }

    private void setNewInitialEventCoordinates(MouseEvent event) {
        parentMinX = image.getBoundsInParent().getMinX();
        parentMinY = image.getBoundsInParent().getMinY();
        parentHeight = image.getBoundsInParent().getHeight();
        parentWidth = image.getBoundsInParent().getWidth();
        clickX = event.getX();
        clickY = event.getY();
    }
   
    
    private void resizeHeight(double height){
    		if (minH>height){
		return;
		}
    	component.setImageHeight(height);
}

    public static void makeResizable(ImagePath component, Position aPos) {
        final DragAndResize resizer = new DragAndResize(component, aPos); 
        ImageView anImage = component.getImageView();
        anImage.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mousePressed(event);
            }});
        anImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mouseDragged(event);
            }});
        anImage.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mouseOver(event);
            }});
        anImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resizer.mouseReleased(event);
            }});
    }

    protected void mouseOver(MouseEvent event) {
    		setNewInitialEventCoordinates(event);
        if(isInResizeZone(event) || resizing) {
            image.setCursor(Cursor.S_RESIZE);
        }
        else {
            image.setCursor(Cursor.DEFAULT);
        }
    }
    
    protected void mousePressed(MouseEvent event) {
      	setNewInitialEventCoordinates(event);
        dragging = !isInResizeZone(event);
        resizing = isInResizeZone(event);
    }
    
    protected boolean isInResizeZone(MouseEvent event) {
        return isInBottomResize(event);  	
    }
     
    
    private boolean isInBottomResize(MouseEvent event){
    		double innerBottomSide = parentHeight - margin;
		double outerBottomSide = parentHeight;
		return ((event.getY() > innerBottomSide) 
				&& (event.getY() < outerBottomSide));
    }
    
    protected void mouseDragged(MouseEvent event) {
    		double mouseX = event.getX() + image.getBoundsInParent().getMinX();
    		double mouseY = event.getY() + image.getBoundsInParent().getMinY();
    		if (dragging){
    				double translateX = mouseX - clickX;
    				double translateY = mouseY - clickY;
    				position.setX(translateX);
    				image.setTranslateX(translateX);
    				position.setY(translateY);
    				image.setTranslateY(translateY);
                return;
    		}
    		else{
        if (isInBottomResize(event) || resizing){
        double newHeight = mouseY - parentMinY;
        resizeHeight(newHeight);
        }
    		}
    }
    
    protected void mouseReleased(MouseEvent event) {
        resizing = false;
        dragging = false;
        image.setCursor(Cursor.DEFAULT);
    }
    
}