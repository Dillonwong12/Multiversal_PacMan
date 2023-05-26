# 23-SEM1-Assignment2-Template

# [SWEN30006 2023S1] Project Assignment 2
PacMan in the TorusVerse 
# Team Members
- 1173104 Erick Wong (<erickw@student.unimelb.edu.au>)
- 1236449 Dillon Han Ren Wong (<dillonhanren@student.unimelb.edu.au>)
- 1272545 Jonathan Linardi (<linardij@student.unimelb.edu.au>)

# Getting Started 

1. Go to File -> Project Structure -> Modules (Sources):

2. Create an 'out' folder under the project (Select the "2023-sem1-projectassignment2..." folder and add "/out" to the end of the file path)

3. Mark the 'pacman' folder as Sources Root

4. (If jdom cannot be found) Go to Libraries tab in Module Settings: 
- Hit the + above JGameGrid
- Add new Java
- Go to the project folder -> pacman -> lib
- Select jdom-1.1.13.jar and add this 

5. Go back to Modules tab (Dependencies)
- Make sure both JGameGrid and jdom-1.1.13 are set to Compile

6. Code alterations:
- In pacman 'Driver.java' file: 
- - Changed DEFAULT_PROPERTIES_PATH to "pacman/properties/test2.properties" 

- In pacman -> editor -> editor -> "Controller.java" file: 
- - Changed TileManager.getTilesFromFolder() to "pacman/sprites.data/";
