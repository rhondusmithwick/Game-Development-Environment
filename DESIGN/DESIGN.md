# Introduction

The goal of this project is to create a program that allows users to create and then play their own games. The user should have ample choice in the visual look of their game, including everything from the game’s environment to how characters move and interact. 

Our chosen game genre is the Fighting Game Genre. We chose this genre because while its mechanics are simple (IE you die when health is 0 and win when your enemy’s is 0), it has a lot of potential for user customization. The user will be able to create their own characters and design the moves that these characters will execute, along with designating exactly what exactly defines a match in their game. Our design will need to support these aspects of character creation and match generation, along with providing ample opportunity for the user to define what enemy characters’ artificial intelligence looks like. Additionally, our design will need to be entirely data-driven to make the back-end as flexible as possible and to help make the process of networking easier (since the network will just have to receive data and will not need to know any information about specific classes).


# Overview

We divided the design based on three components- logic, data, and visualization. Across all these components, we will have an Authoring Environment and Game Play. We use the Entity-Component-System model to define our framework. As a user defines the game through interacting with the GUI, the front end will continually write into an XML configuration file. On game launch,  a Game Builder will build the game through composition, creating Entities and adding Components. These Entities and Components will be managed by Systems that define their logic. The Front End accesses the Back End API and updates after every frame. 

Event handling will be handled by a central event manager based on this reference: http://stackoverflow.com/questions/937302/simple-java-message-dispatching-system. The event dispatcher system consists of listeners that can have access to any aspect of the game (entities, components, systems, etc.). The hope is that we can provide a script editor for Groovy that will allow users to script their own events handlers.

# User Interface

Authoring Environment will have a game screen that allows the user to enter a name, description and icon for their game. It will also describe a game timeline that allows the user to edit the order of their environments.  The environment creation view will have a large display area where the user can see the progress as they build their environment and also interact with the objects and characters and drag them around to place them, and click them to edit their properties. The can also add buttons to the environment and specify actions for them .The area will also have a toolbar with buttons that allow the user to open various menus which will allow them to create new objects or characters, or to change various aspects of the level’s environment such as background image or background music.  Pressing a button will bring up a menu in a new tab or popup that will allow the user to create a new character or a new powerup or object and add it to the currently created level.  The toolbar will also allow them to see all the available objects and place them into the level. The user should be able to define different actions that can then be associated with characters. The user will also be able to define losing and winning conditions for each level. The user will also be able to edit the event manager that specifies what action occurs when two entities collide.

Game player will essentially just consist of a screen that will display the graphical representation of the game. There will be a game status screen that shows the current games status and stats.

# Design Details 

Frontend Classes
* Authoring Environment View -> contain and control the other views
    - Contains all sub views in a data structure such as a tab pane
    - Manages the creation of new sub views based on user input in other sub views
        + Example: user presses new environment button in game view which prompts the authoring environment view to open a new environment view instance
* Game View -> Edit game details 
    - Allows user to edit game name, description, and icon. It also allows the user to open instances of the timeline editor and the entity editor to create components for their game.
* Environment View -> Allows the user to create specific environments/levels
    - Will allow user to see all entities created using the entity editor and allow them to be placed
* Editor Abstract Class -> Sets up editor environment hierarchy 
    - Sets use the basic layout of an editor 
    - Contains add methods and display methods common among all editors
    - Contains components to allow for and save user input
* Entity Editor -> Allows user to add a game entity (ie. character, power up, etc..)
    - Allows user to add components to an entity
    - Displays necessary parameters the user must fill in
    - THROWS EXCEPTION when the user fails to specify necessary fields
    - THROWS EXCEPTION when the user enters unfeasible values 
* Environment Editor -> Allows user to define an environment (ie. a level, a mode, a menu/screen)
    - Will show the user all currently created entities that they can use and allow them to place them on the game screen. Will allow them to click on the entity’s image to open up an entity editor and edit the entity.
* Event Editor -> Allows the user to edit events that occur within the flow of the game such as what happens when two objects collide
* Timeline Editor -> allow user to edit the flow of levels to create games that encompass multiple number of levels and to add in character selection menus or upgrade menus in between combat levels
    - Allows users to define a series of environments that will be displayed or played through
    - User will define conditions to transition from environment to environment 
* Data writer -> write created environments, entities, and game details to data files in specified format
* Data reader -> read game files to recreate previously saved games and allow further editing
* Button Factory -> allows users to create new buttons on an environment with user defined function
* HeadsUpDisplay Factory -> allows users to create new HeadsUpDisplays to display stats or conditions
* GamePlayer →  Reads the finished data of a game, and in conjunction with the Game Engine, allows the player to effectively play the created game

Backend

* Entity-Component-Systems architecture
    - Entity contains components which define its data (Model)
    - Components contain specific data (Model)
    - Systems act on components as logic (Controllers)
* Entity - only one class
    - Contains list of components and unique ID
    - Allows to get a component based on class Type and index of which component to get if there are multiple
    - Managed by an EntitySystem 
    - Only one Entity class because this will allow users to dynamically generate different types of entities based on what type of components an entity is composed of
* Components - implement Component interface
    - Contain data
    - Held by Entities (containers) and systems (which act on them)
    - Some Components only allow their entity to have on of them
        + EX: Position 
    - Others allow multiple
        + EX: Attack
* Systems - implment ISystem
    - Act on components as logic 
        + Examples include MovementSystem, SpriteSystem, etc
    - Held in a SystemsManager 
    - Three systems
        + EntitySystem: contains all the entities
        + PhysicsSystem: contains physics components
        + EventsSystem: contains event dispatcher and listeners 
        + Entities map Component classes to their instances of that Component class
        + When a system runs, it grabs the instances that it needs
        + EX: CollisionSystem would get the Collision.class entries from the map
* Frontend and authoring environment will need to be able to create components that an entity needs
* Game Authoring environment will need to be able to save the entity system, which the game engine will need to load up
* Will rely on both properties files to specify locations of classes for reflection and serialization files to load up and save objects


# Example games

Describe three example games from your genre in detail that differ significantly. Clearly identify how the functional differences in these games is supported by your design and enabled by your authoring environment. Use these examples to help make concrete the abstractions in your design.

# Design Considerations 

This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. Include any design decisions that each sub-team discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design.
