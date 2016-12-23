package pl.nikowis.engineTester;

import org.lwjgl.opengl.Display;
import pl.nikowis.renderEngine.DisplayManager;
import pl.nikowis.renderEngine.Loader;
import pl.nikowis.renderEngine.RawModel;
import pl.nikowis.renderEngine.Renderer;

/**
 * Created by Nikodem on 12/22/2016.
 */
public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,

                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        RawModel model = loader.loadtoVAO(vertices);

        while (!Display.isCloseRequested()) {
            renderer.prepare();

            renderer.render(model);
            DisplayManager.updateDisplay();
        }
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
