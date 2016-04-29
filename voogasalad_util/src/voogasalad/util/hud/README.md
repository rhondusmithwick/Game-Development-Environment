# VoogaSalad TLGCS HUD Utility

This HUD utility allows for many different projects to implement a real-time heads up display of user-chosen fields, such as points, health, ammo, etc. This package can be implemented in three main steps.

## 1. Setting Up Authoring Environment

Two simple changes need to be made:

1. The authoring environment needs to have a button that will create a new `PopupWindow` when clicked. That `PopupWindow` is a text area where users can enter the fields they want to show, in order.

2. The authoring environment needs to have one class implement the `IAuthoringHUDController` interface. The implementing class will receive the file location (in string format) for the user HUD preferences. It is recommended that the implementing class be one of the classes that is serialized so that when the game engine is started, it can immediately have a reference to the preference file location. Refer to the `ExampleMain` class inside `examples`.

## 2. Setting Up Game Engine

Three changes need to be made:

1. Modify any fields that you would want to observe (such as points, health, etc.) to use properties instead of integers, doubles, etc. This is made much easier by the fact that Property can take in any sort of object as their value (and any primitives are autoboxed to their object counterparts).

2. Implement `IValueFinder`. This has some internal steps of its own.

  * Have your implementation reference some kind of dataSource object. This object is the class that holds most of your game data (levels, characters, etc.) as the game engine runs.

  * Write the `find()` method. This value maps the string that the user inputted to the place inside the game where that relevant property is actually found. This is the most code-heavy step, and the `ExampleValueFinder` class inside `examples` may help significantly at this step.

3. Place the `HUDController` inside the engine where it can be easily initialized. Keep a reference to it, because it will be necessary to extract the view through the controller when setting up the game player.

## 3. Setting Up Game Player

This step is relatively easy. The `HUDScreen` class comes ready-to-go, and if you use `getScene()` to grab the `SubScene` that the screen is embedded on, you can place the screen anywhere on your existing view. **Advanced:** You may also choose to write your own custom `HUDScreen` class that extends `AbstractHUDScreen`.


If you have any questions, please feel free to contact me at bobby.wang@duke.edu. Thank you for considering this package, and I hope you find it useful!

*- TLGCS Team*
