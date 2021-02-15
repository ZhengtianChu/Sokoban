## Introduction
Zhengtian CHU (scyzc3), Tested on Windows 10, Maven Build Script, Java 15, JavaFX 11, final grades: 95/100.

## Contents
### New Classes
- Source code in ```src/main/java```:
  ```
  display:
      FileHandler.java
      GamePage.java
      StartPage.java
      ScoreBoardAdapter.java
  element:
      EngineFactoryPattern.java
      ScoreList.java
  interact:
      controller:
          GamePageController.java
          ScoreBoardController.java
          StartPageController.java
      model:
          GamePageModel.java
      view:
          GamePageView.java
  ```
- Files in ```resource/display```:
  ```
  ScoreBoardView.fxml
  StartPageView.fxml
  style.css
  ```
- Junit tests in ```test/java```
  ```
  display:
      GameCoreTest.java
  element:
      GameEngineTest.java
      GameObjectTest.java
      GameGridTest.java
      LevelTest.java
      ScoreListTest.java
      GraphicObjectTest.java
      MusicPlayerTest.java
  ```
### Modified Classes
- Disassemble from `````Main.java`````:
    ```
    GameCore.java 
    GameEngine.java 
    MusicPlayer.java
    GameDialog.java
    ```
- Modify origin classes: 
    ```
    Level.java
    GameEngine.java
    GameGrid.java
    GameLogger.java
    GameObject.java
    GraphicObject.java
    ```
  
## How to compile the code
- **Use IDEs (IntelliJ or Eclipse)**:
  - Run the ```StartPage.java``` in ```src/main/java/display``` as main class.
- **Use Maven**:
  - Terminal command: ```mvn clean: javafx:run```
    
## Javadoc
- Javadocs have been added to all the classes in *src/main/java*.
- Javadocs have been stored in *javadoc*.

## Functionality
### Implemented and Worked Properly Features
#### 1. Background Music 
Turn on and turn off background music.
#### 2. Undo
#### 3. Reset Level
#### 4. Score Board
After finishing each level, the player can see a score board which records the top 10 score in this level; And after finishing whole game, they can see a permanent rank list for the whole game.
#### 5. Save and Load Game
#### 6. Real-time Status Display
Players can check their moves count, time-consuming and level index when playing.
#### 7. A Start Page
#### 8. Choose the Wall Color
#### 9. UI Design
Add a style sheet for the start page and score board.
#### 10. Reward and Penalty
Players can pick the **star** to reduce the time-consuming. If they step on the **bomb**, it will blow them up.
#### 11. Additional level

### Implemented but Flawed Features
#### 1. Portal
A portal can transfer the keeper from one location to another, but is not work well when using undo. 
### Unimplemented Features
#### 1.Teaching Level
I want to create a teaching level to introduce players to the rules of pushing boxes and how to operate them.
#### 2. Cannot deal with maps if the box and the diamond overlap in the beginning

## Refactoring
### Design Patterns
- Use **MVC modern** to keep the model away from the GUI implementation. FXML document is used for GUI design. Main process happens in model and use controller to handle events. For example,
    ```
    StartPageModel.java
    StartPageView.fxml
    StartPageController.java
    ```

- Use **Singleton pattern** to generate only one MusicPlayer instance so that the music player won't be generated again. 
    ```
    MusicPlayer player = MusicPlayer.getInstance()
    ``` 
  
- Use **Simple Factory Pattern** to manufacture game engine from engine factory. You can check it in ```EngineFactoryPattern.java```.

- Use **Adapter Pattern** to adapt two different kinds of score boards. You can check it in ```ScoreBoardAdapter.java```.

### Junit Test
Add useful unit tests for classes in ```src/main/java```.

### Maven
Use **maven** to build the project. All the dependency has been wrote in ```pom.xml```.

### Source Code Tree
```
├───main
│   ├───java
│   │   ├───display
│   │   ├───element
│   │   └───interact
│   │       ├───controller
│   │       ├───model
│   │       └───view
│   └───resources
│       ├───display
│       ├───element
│       └───scoreboard
└───test
    ├───java
    │   ├───display
    │   └───element
    └───resources
```

### Bob's Convention
Use **Bob's Convention** to modify code and remove some unused or duplicated code.`



