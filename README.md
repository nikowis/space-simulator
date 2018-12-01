## Simulator
Simulator project is an academic project with 3D graphics using OpenGL.
The project goal was to present different shading and reflection models.

### Setting up the project in NetBeans 8.2
* Download the project
* In NetBeans create a new project using the "Java project with existing sources" template (select all source files)
* In the project Properties -> Libraries -> Compile tab : add jars from the /lib/jars/
* In the project Properties -> Run : select the Main class ( pl.nikowis.MainGameLoop ) and add " -Djava.library.path=lib/natives" to VM Options
* Run the project!

### Manual

Car movement using arrow keys.

#### Key bidings to change game settings :
 * N - turn off the textures
 * M - turn on the textures
 * 1 - Moving camera
 * 2 - Static camera
 * GRAVE/ESCAPE - toggle sample GUI
 * 3/4 - toggle environment mapping technique
 *

### Camera movements
 * AWSD - moving
 * Arrows - turning
 * LSHIFT/SPACE - move along Y axis



### Used technologies :
* Java 8
* LWJGL 2.9.1
* Slick-util
