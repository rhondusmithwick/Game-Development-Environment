# Intro
This utility is meant to help you incorporate animation into your project.

### Documentation
Please see our [Documentation](google.com). Especially take note of the animation package, which you will need to use in your project.

### How To Run the Application
1. Run the application with the Main class in the main package. Upon running the Main class a selection screen should appear that allows you to browse all the files on your computer. Use this screen to select the sprite sheet you would like to use for animation. A sprite sheet is a large image that contains multiple images of the sprite in multiple positions that when flashed through quickly look like movement.
2. Once a sprite sheet is selected, click OK and you'll be on the main screen. Here you will see your sprite sheet in the center, control buttons on the right, and an animation name and a duration slider on the left.
3. Add frames by drawing rectangles around the screen with you mouse. There is an add frame button the right. You can also delete a frame by clicking it and hitting delete frame. In addition to drawing, we allow you to move the frames around and press ENTER to add it.
4. As frames are added, they will also pop up as buttons on the left. These buttons can be clicked to view and edit the frame characteristics, such as height, width, and x/y position. The duration slider controls the length of the animation with smaller numbers meaning a faster animation because it will last less time.
5. When a frame is set, a red number should appear within the frame in the top left corner indicating the frame's number and thus order of appearance within the animation. After creating several frames, preview the animation with the 'Preview Animation' button. A text field in the top left corner will indicate if the animation is saved or not. Use the 'Save Button' to save the current frames for the current animation.
6. Be sure to set the name of your animation. Save it, and it will be saved to a map of animations. All these animations will be accessible from the same properties file. The properties file contains several "moves" that can be used.

***Note:***
Most sprite sheets contain a background. We have accounted for this by including an activate transparency button. When this button is pressed,the cursor changes to indicate this change transparency state. While this mode is activated, any color that is equal to the pixel pressed/clicked by the cursor until another color is encountered will disappear and become transparent. Multiple clicks will remove the background or any other features you wish to become transparent in your image. This feature can be used on any image you choose to import. Just click the save image button to save this new altered image.
***Note***
Be Sure to save your images if you modify them!

### Using it in Your application
***Note***
Please see testing.SandBoxFX for an example of using the animation

To use the sprite utility in your project:

1. Use the Sprite Utility to create a properties file.
2. In your project, have an imageView as normal.
3. Create an animation using the Animation Factory class and the bundle. You need to know the name of the move you would like to use. EX:
```java
Animation megaManAnimation = createAnimationFromBundle(imageView, RESOURCE_BUNDLE, "ball");
```
***Note***
You can also use AnimationFactory to create the Animation directly from the screen, but it is strongly recommended that you use the bundle.

### Utility Controls
* Click and drag on the sprite sheet to draw a rectangle.
* Press 'Enter' or click the 'Add Frame' button in order to set the selected rectangle and add it as a frame
* Use the arrow keys to move a same size frame/selected frame into a new location
* Use the duration slider to control the speed of the animation
* Use the text field to name the animation
* Use the 'Save Animation To File' button to safe the current animation
* Use the 'New Animation' button to start a new animation
* Use the 'New Sprite Sheet' button to import a new sprite sheet
* Use the 'Preview Animation' button to preview whatever frames are currently set
* Use the 'Delete Frame' button to delete a frame
* Use the 'Activate Transparency' button to remove the background of the sprite sheet
* Use the 'Save Image' button to save the image you are currently working on (particularly if the background was removed)
* Click the 'Frame #' buttons on the left to view and edit frame properties
