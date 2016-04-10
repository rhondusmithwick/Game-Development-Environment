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
* Will rely on both properties files to specify locations of classes for reflection and datamanagement files to load up and save objects


# Example games

* BOXING-esque Game:  This game has two characters playing against each other.  After a certain amount of time, a winner is declared based on who has inflicted the most damage. The authoring environment will allow for the user to create characters and the timer as “entities” with various “components.”  For example, a game timer entity would likely have a component (maybe TimeComponent) that sets how long a round is supposed to last.  Character entities may have components like DamageComponent, ImageComponent, MoveComponent, etc.  Win conditions are decided based on the state of each of the entities (comparing the damage inflicted by each entity when the timer is 0, having a player win when the other player’s health reaches 0, etc.).


* MORTAL KOMBAT-esque Game:  This type of game is a traditional 2D fighter.  There are two characters playing against each other, and the game finishes when one character’s health is fully depleted.  The player character progresses through a series of levels in order to win.The differing Win Conditions will be handled by setting an event handler based on the state of the components of the various entities (to be set by the user).  

* SMASH-esque Game:  This type of game is a “platformer”-type of fighting game.  There can be more than two players, items, and a more intricate stage with platforms (rather than just a simple stage of the likes of Mortal Kombat/Street Fighter).  Again, this is handled by the entity/component system of the back-end.  There’s no inherent limit to the number of characters that can be added by the user, and “Items” can be an entity of their own (with Components that specify what the item does).  Similarly, “Platforms” can also be entities of their own and added freely to the game.

# Design Considerations 

* All classes implement Serializable and should be able to written and loaded
    - Use Serializable or XML
    - Benefits of XML: readable, user can edit
    - Benefits of Serializable: know it works
    - JSON? For networking?
    - Network is “dumb”: only passes messages, wrapped passages 
    - Where are those messages formed? 
* We think controllers will give us more flexibility than observables (bc with Observables there is only one update method, and we need to typecast) 
    - Entity Component System 
    - Id -> Component
    - Example of a component is Health (has its own class)
* Internal API’s 
   - Physics 
       + Collision type (collidable or no) component 
   - Component Interface (get or set a certain game character properties)
       + Getter + Setter, generic type value for Component class
       + We can add complexity to the components (Moves might be one complex component)
       + Resource files to keep track of which components a character/sprite needs + which generic types to take in  
   - Adding a new component
       + It is basically just adding to resource files.
       + Check out the Strategy Design Pattern.

Frontend
* Authoring Environment
    - Inheritance Hierarchy for objects/characters or some way to abstract them since they will be mostly the same but differ in details
        + Need to be able to set the properties of these objects based on user input/randomly generate certain ones
    - An action object/ some way to assign a certain keys input for a character to a certain action. Ex. assign right arrow to move character right - > will be a component 
    - Probably will be mostly menus so an inheritance hierarchy to create new menus to allow more customization makes sense
        + Create characters
        + Create objects that characters can interact with
        + Create the game environment
        + Define the flow of the game by switching the order of levels
        + Define End conditions for each level, winning and losing
    - Allow user to load previous games using a menu that pops up at begining or allow them to start with blank slate
    - Allow them to create char selection screen that will display all characters and allow them to choose
        + Provide framework for one type to start, eg Super Smash Bros selection screen type
        + Display this before all user created levels
    - Potential Classes:
        + Utilities Classes -> Enums, Useful Abstractions, Reused general functions
        + PlayView 
        + CreateMenu
        + Menu -> abstract, allow for easy creation of new menu types
        + MainView
        + DefineRules -> define end conditions, losing winning
        + DefineTournament -> Define level order
        + DefineCharacters 
        + DefineObjects
        + DefineEnvironment -> Set background, theme music, health bars, timers etc.
        + The four above could possibly be abstracted into a hierarchy
        + Action Class -> allow input to be assigned to action
* Player
    - Menu to load game first thing that is shown
    - From data file, create all needed objects, place them on screen
    - After reading the datafile and loading the game, all the code to run the game should hopefully be contained within the objects (e.g. all the actions should be set up so if the user enter anything, the program automatically reacts)














