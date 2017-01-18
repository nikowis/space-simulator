## Simulator
Simulator project is an academic project with 3D graphics using OpenGL.
The project goal was to present different shading and reflection models.

### Setting up the project in NetBeans 8.2
* Download the project
* In NetBeans create a new project using the "Java project with existing sources" template (select all source files)
* In the project Properties -> Libraries -> Compile tab : add jars from the /lib/jars/
* In the project Properties -> Run : select the Main class ( pl.nikowis.engineTester.MainGameLoop ) and add " -Djava.library.path=lib/natives" to VM Options
* Run the project!

### Manual

Car movement using arrow keys.

#### Key bidings to change game settings :
 * N - turn off the textures
 * M - turn on the textures
 * 1 - Static camera
 * 2 - Third person camera
 * 3 - Turning camera
 
#### Shading models (turn off the textures first)
 * F - Flat shading
 * G - Gouraud shading
 * H - Phong shading
 
#### Reflection models (turn off the textures first)
 * I - Turn off reflections
 * O - Blinn–Phong reflection model
 * P - Phong reflection model

#### Mouse movements while using third person camera
 * Scroll up + down for camera distance
 * Left mouse button + move left/right - left/right camera movement
 * Right mouse button + move up/down - up/down camera movement

### Used technologies :
* Java 8
* LWJGL 2.9.1
* Slick-util
