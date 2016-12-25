package pl.nikowis.engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.Camera;
import pl.nikowis.entities.Entity;
import pl.nikowis.models.RawModel;
import pl.nikowis.models.TexturedModel;
import pl.nikowis.renderEngine.DisplayManager;
import pl.nikowis.renderEngine.Loader;
import pl.nikowis.renderEngine.Renderer;
import pl.nikowis.shaders.StaticShader;
import pl.nikowis.textures.ModelTexture;

/**
 * Created by Nikodem on 12/22/2016.
 */
public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };


        RawModel model = loader.loadtoVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("texture"));
        TexturedModel staticModel = new TexturedModel(model, texture);

        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);
        Camera camera = new Camera();

        while (!Display.isCloseRequested()) {
            entity.increasePosition(0, 0, -0.2f);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
