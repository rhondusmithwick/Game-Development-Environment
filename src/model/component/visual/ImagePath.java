package model.component.visual;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.List;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utility.SingleProperty;
import utility.TwoProperty;

/**
 * Component to hold an imagePath.
 *
 * @author Rhondu Smithwick
 */
public class ImagePath implements IComponent {

        /**
         * The singleProperty.
         */
        private final SingleProperty<String> imagePathProperty;
        private final TwoProperty<Double, Double> imageSizeProperty;
        private transient ImageView imageView;
        private Rectangle2D viewport;
        private int frameIndex;
        private final int maxFrameIndex;
        private boolean isAnimated;
        private double timeSinceLastFrame, elapsedTime;
        private final double frameDuration, totalDuration;

        public ImagePath() {
                this("resources/RhonduSmithwick.JPG");
        }

        /**
         * Construct with no animation.
         *
         * @param imagePath
         *            starting value
         */
        public ImagePath(String imagePath) { // TODO: place default in resource file
                this(imagePath, 0.0, 0.0, new Rectangle2D(0, 0, 0, 0), false, 0, 0, 0);
        }

        // TODO: IMPORTANT NOTE: I forgot to account for columns!
        /**
         * Construct with starting values.
         *
         * @param imagePath
         *            String path to image
         * @param imageWidth
         *            width of image
         * @param imageHeight
         *            height of image
         * @param spritesheetPath
         *            String path to spritesheet
         * @param width
         *            width of viewport
         * @param height
         *            height of viewport
         * @param offsetX
         *            offset in x-direction
         * @param offsetX
         *            offset in y-direction
         */
        public ImagePath(String imagePath, double imageWidth, double imageHeight, 
                        Rectangle2D viewport, boolean isAnimated, double frameDurationMillis, double totalDurationMillis,
                        int maxFrameIndex) {
                this.imagePathProperty = new SingleProperty<>("ImagePath", imagePath);
                this.imageSizeProperty = new TwoProperty<>("ImageWidth", imageWidth, "ImageHeight", imageHeight);

                File resource = new File(imagePath);
                Image image = new Image(resource.toURI().toString());
                this.imageView = new ImageView(image);

                this.viewport = viewport;
                this.frameIndex = 0;
                this.isAnimated = isAnimated;
                this.reset();
                this.frameDuration = frameDurationMillis;
                this.totalDuration = totalDurationMillis;
                this.maxFrameIndex = maxFrameIndex;
        }

        /**
         * Get the imagePath property.
         *
         * @return impagePath string property
         */
        public SimpleObjectProperty<String> imagePathProperty() {
                return imagePathProperty.property1();
        }

        public String getImagePath() {
                return imagePathProperty().get();
        }

        public void setImagePath(String imagePath) {
                this.imagePathProperty().set(imagePath);
                File resource = new File(imagePath);
                Image image = new Image(resource.toURI().toString());
                this.imageView.setImage(image);
                
        }

        public SimpleObjectProperty<Double> imageWidthProperty() {
                return imageSizeProperty.property1();
        }

        public SimpleObjectProperty<Double> imageHeightProperty() {
                return imageSizeProperty.property2();
        }

        public double getImageWidth() {
                return this.imageWidthProperty().get();
        }

        public void setImageWidth(double imageWidth) {
                this.imageWidthProperty().set(imageWidth);
                imageView.setFitWidth(imageWidthProperty().get());
        }

        public double getImageHeight() {
                return this.imageHeightProperty().get();
        }

        public void setImageHeight(double imageHeight) {
                this.imageHeightProperty().set(imageHeight);
                imageView.setFitHeight(imageHeightProperty().get());
        }

        @Override
        public List<SimpleObjectProperty<?>> getProperties() {
                return Arrays.asList(imagePathProperty(), imageWidthProperty(), imageHeightProperty());
        }

        private void updateViewport() {
                double width = this.viewport.getWidth();
                double height = this.viewport.getHeight();
                double offsetX = this.frameIndex * width; // TODO: change to offsetX +
                                                                                                        // ...
                double offsetY = 0.0; // TODO: change to offsetX + ...
                this.viewport = new Rectangle2D(offsetX, offsetY, width, height);
        }

        public Rectangle2D getViewport() { // TODO: remove, for debugging purposes
                return this.viewport;
        }

        public ImageView getImageView() { // TODO: make imageView an instance
                                                                                // variable
                imageView.setViewport(this.viewport); // TODO: for some reason, setting
                                                                                                // viewport internally fails
                return imageView;
        }

        public void setFrameIndex(int frameIndex) {
                this.frameIndex = frameIndex % this.maxFrameIndex;
                this.updateViewport(); // TODO: possibly relocate this, hacky
        }

        public void updateTime(double dt) {
                this.elapsedTime += dt;
                this.timeSinceLastFrame += dt;
        }

        public double getElapsedTime() {
                return this.elapsedTime;
        }

        public double getTimeSinceLastFrame() {
                return this.timeSinceLastFrame;
        }

        public double getFrameDuration() {
                return this.frameDuration;
        }

        public void resetTimeSinceLastFrame() {
                this.timeSinceLastFrame = 0.0;
        }

        public void reset() {
                this.timeSinceLastFrame = 0.0;
                this.elapsedTime = 0.0;
        }

        public double getDuration() {
                return this.totalDuration;
        }

        public int getFrameIndex() {
                return this.frameIndex;
        }

        public void incrementFrameIndex() {
                this.setFrameIndex(this.getFrameIndex() + 1);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
                in.defaultReadObject();
                // reconstruct imageView
                File resource = new File(this.imagePathProperty().get());
                Image image = new Image(resource.toURI().toString());
                this.imageView = new ImageView(image);
                imageView.setFitHeight(imageHeightProperty().get());
                imageView.setFitWidth(imageWidthProperty().get());
                imageView.setPreserveRatio(true);
        }

}