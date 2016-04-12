package view;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.component.movement.Position;
import model.component.visual.ImagePath;

public class DragAndResize {
        
    private double margin = 8;

    private final ImageView image;
    
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
	private ImagePath path;
	private Position position;
    
    private DragAndResize(ImageView anImage, ImagePath aPath, Position aPos) {
        image = anImage;
        path = aPath;
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
    
    private void resizeWidth(double width){
    	if (minW>width){
    			return;
    		}
    		path.setImageWidth(width);
    		//image.setFitWidth(width);
    }
    
    private void resizeHeight(double height){
    		if (minH>height){
		return;
		}
    		path.setImageHeight(height);
		//image.setFitHeight(height);
}

    public static void makeResizable(ImageView anImage, ImagePath aPath, Position aPos) {
        final DragAndResize resizer = new DragAndResize(anImage, aPath, aPos); 
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
        return isInRightResize(event) || isInBottomResize(event);  	
    }
       
    private boolean isInRightResize(MouseEvent event){
    		double innerRightSide = parentWidth - margin;
		double outerRightSide = parentWidth;
		return  ((event.getX() > innerRightSide)
				&& (event.getX() < outerRightSide));
    }
    
    private boolean isInBottomResize(MouseEvent event){
    		double innerBottomSide = parentHeight - margin;
		double outerBottomSide = parentHeight;
		//System.out.println("outerBottomSide "+ outerBottomSide );
	    //System.out.println("event.Y()" + event.getY());
		//System.out.println("innerBottomSide " + innerBottomSide );
		return ((event.getY() > innerBottomSide) 
				&& (event.getY() < outerBottomSide));
    }
    
    protected void mouseDragged(MouseEvent event) {
    		//double offsetX = event.getSceneX() - sceneX;
        //double offsetY = event.getSceneY() - sceneY;
        //double newTranslateX = translateX + offsetX;
        //double newTranslateY = translateY + offsetY;
    		double mouseX = event.getX() + image.getBoundsInParent().getMinX();
    		double mouseY = event.getY() + image.getBoundsInParent().getMinY();
    		if (dragging){
    				double translateX = mouseX - clickX;
    				double translateY = mouseY - clickY;
    				position.setX(translateX);
    				position.setY(translateY);
    				//image.setTranslateX(translateX);
    				//image.setTranslateY(translateY);
    			   // image.setX(translateX);
    			   //image.setY(translateY);
                return;
    		}
    		else{
        if (isInBottomResize(event) || resizing){
        double newHeight = mouseY - parentMinY;
        resizeHeight(newHeight);
        }
        if (isInRightResize(event) || resizing){
        	double newWidth = parentWidth + parentMinX - mouseX;
        	resizeWidth(newWidth);
        }  
    		}
    }
    
    protected void mouseReleased(MouseEvent event) {
        resizing = false;
        dragging = false;
        image.setCursor(Cursor.DEFAULT);
    }
    
}