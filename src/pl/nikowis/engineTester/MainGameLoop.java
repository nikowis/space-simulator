package pl.nikowis.engineTester;

import org.lwjgl.opengl.Display;
import pl.nikowis.renderEngine.DisplayManager;

/**
 * Created by Nikodem on 12/22/2016.
 */
public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        while (!Display.isCloseRequested()) {
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }
}
