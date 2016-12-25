package pl.nikowis.engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.Camera;
import pl.nikowis.entities.Entity;
import pl.nikowis.entities.Light;
import pl.nikowis.models.RawModel;
import pl.nikowis.models.TexturedModel;
import pl.nikowis.renderEngine.DisplayManager;
import pl.nikowis.renderEngine.Loader;
import pl.nikowis.renderEngine.OBJLoader;
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


        RawModel model = OBJLoader.loadObjModel("stall", loader);

        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(100);
        texture.setReflectivity(1);
        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -40), 0, 0, 0, 1);
        Camera camera = new Camera();
        Light light = new Light(new Vector3f(0, 10, 0), new Vector3f(1, 1, 1));

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
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
