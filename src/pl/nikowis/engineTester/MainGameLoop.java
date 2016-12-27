package pl.nikowis.engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.Camera;
import pl.nikowis.entities.Entity;
import pl.nikowis.entities.Light;
import pl.nikowis.entities.Player;
import pl.nikowis.models.RawModel;
import pl.nikowis.models.TexturedModel;
import pl.nikowis.renderEngine.DisplayManager;
import pl.nikowis.renderEngine.Loader;
import pl.nikowis.renderEngine.MasterRenderer;
import pl.nikowis.renderEngine.OBJLoader;
import pl.nikowis.terrains.Terrain;
import pl.nikowis.textures.ModelTexture;
import pl.nikowis.textures.TerrainTexture;
import pl.nikowis.textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Nikodem on 12/22/2016.
 */
public class MainGameLoop {

    public static final int TREE_OUTLINE_STEP = 5;

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel rawLampModel = OBJLoader.loadObjModel("lamp", loader);
        TexturedModel staticLampModel = new TexturedModel(rawLampModel, new ModelTexture(loader.loadTexture("lamp")));


        RawModel rawTreeModel = OBJLoader.loadObjModel("tree", loader);
        TexturedModel staticTreeModel = new TexturedModel(rawTreeModel, new ModelTexture(loader.loadTexture("tree")));

        RawModel rawPersonModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel staticPersonModel = new TexturedModel(rawPersonModel, new ModelTexture(loader.loadTexture("playerTexture")));

        List<Entity> entities = new ArrayList<Entity>();
        createTreesInTheMiddle(staticTreeModel, entities);
        createTreeOutline(staticTreeModel, entities);


        entities.add(new Entity(staticLampModel, new Vector3f(-100, 0, -40), 0, 0, 0, 1));
        entities.add(new Entity(staticLampModel, new Vector3f(0, 0, -40), 0, 0, 0, 1));
        entities.add(new Entity(staticLampModel, new Vector3f(100, 0, -40), 0, 0, 0, 1));

        Player player = new Player(staticPersonModel, new Vector3f(240, 0, 450), 0, 0, 0, 0.7f);

        Light sunLight = new Light(new Vector3f(0, 10000, -7000), new Vector3f(0.4f, 0.4f, 0.4f));
        List<Light> lights = new ArrayList<>();
        lights.add(sunLight);
        lights.add(new Light(new Vector3f(-100, 4, -40), new Vector3f(5, 0, 0), new Vector3f(1, 0.01f, 0.02f)));
        lights.add(new Light(new Vector3f(0, 4, -40), new Vector3f(5, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(100, 4, -40), new Vector3f(5, 0, 0), new Vector3f(1, 0.01f, 0.02f)));

        //####################################################################
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap2"));

        //####################################################################

        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap);

        Camera camera = new Camera(player);
        MasterRenderer renderer = new MasterRenderer();

        ModelTexture texture = staticTreeModel.getTexture();
        texture.setShineDamper(100);
        texture.setReflectivity(1);

        while (!Display.isCloseRequested()) {
            camera.move();
            player.move();
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            //renderer.processTerrain(terrain2);
            for (Entity entity : entities) {
                renderer.processEntity(entity);
            }

            renderer.render(lights, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static void createTreeOutline(TexturedModel staticTreeModel, List<Entity> entities) {
        for (int i = 0; i < Terrain.SIZE; i += TREE_OUTLINE_STEP) {
            entities.add(new Entity(staticTreeModel, new Vector3f(0, 0, i), 0, 0, 0, 7));
        }
        for (int i = 0; i < Terrain.SIZE; i += TREE_OUTLINE_STEP) {
            entities.add(new Entity(staticTreeModel, new Vector3f(i, 0, 0), 0, 0, 0, 7));
        }
        for (int i = 0; i < Terrain.SIZE; i += TREE_OUTLINE_STEP) {
            entities.add(new Entity(staticTreeModel, new Vector3f(Terrain.SIZE, 0, i), 0, 0, 0, 7));
        }
        for (int i = 0; i < Terrain.SIZE; i += TREE_OUTLINE_STEP) {
            entities.add(new Entity(staticTreeModel, new Vector3f(i, 0, Terrain.SIZE), 0, 0, 0, 7));
        }
    }

    private static void createTreesInTheMiddle(TexturedModel staticTreeModel, List<Entity> entities) {
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            int xCoord = 230 + random.nextInt(450);
            int zCoord = 490 + random.nextInt((int) (Terrain.SIZE / 4));
            entities.add(new Entity(staticTreeModel, new Vector3f(xCoord, 0, zCoord), 0, 0, 0, 7));
        }
    }
}
