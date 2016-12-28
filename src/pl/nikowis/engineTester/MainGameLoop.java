package pl.nikowis.engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.Camera;
import pl.nikowis.entities.Entity;
import pl.nikowis.entities.Light;
import pl.nikowis.entities.MovingEntity;
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

        createTreeOutline(staticTreeModel, entities);


        MovingEntity player = new MovingEntity(staticPersonModel, new Vector3f(240, 0, 450), 0, 0, 0, 0.7f);

        List<Light> lights = new ArrayList<>();
        setupLights(staticLampModel, entities, lights);

        //###############################   TERRAIN  #####################################
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

        renderer.processEntity(player);
        renderer.processTerrain(terrain);
        for (Entity entity : entities) {
            renderer.processEntity(entity);
        }

        while (!Display.isCloseRequested()) {
            camera.move();
            player.move();
            renderer.render(lights, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static void setupLights(TexturedModel staticLampModel, List<Entity> entities, List<Light> lights) {
        Light sunLight = new Light(new Vector3f(0, 10000, -7000), new Vector3f(0.4f, 0.4f, 0.4f));
        createLamps(staticLampModel, entities);
        lights.add(sunLight);
        for (int i = 0; i < entities.size(); i++) {
            Entity en = entities.get(i);
            if (en.getLight() != null) {
                lights.add(en.getLight());
            }
        }
    }

    private static void createLamps(TexturedModel staticLampModel, List<Entity> entities) {

        float atBaseFact = 1;
        float atDistFact = 0f;
        float atDistSquareFact = 0.0001f;
        Vector3f atenuation = new Vector3f(atBaseFact, atDistFact, atDistSquareFact);

        Light light = new Light(new Vector3f(0, 0, 0), new Vector3f(8, 8, 8), atenuation);
        Light light2 = new Light(new Vector3f(0, 0, 0), new Vector3f(0, 8, 0), atenuation);
        Light light3 = new Light(new Vector3f(0, 0, 0), new Vector3f(0, 0, 8), atenuation);
        Light light4 = new Light(new Vector3f(0, 0, 0), new Vector3f(8, 0, 0), atenuation);
        Light light5 = new Light(new Vector3f(0, 0, 0), new Vector3f(8, 8, 8), atenuation);
        Light light6 = new Light(new Vector3f(0, 0, 0), new Vector3f(8, 8, 8), atenuation);
        Light light7 = new Light(new Vector3f(0, 0, 0), new Vector3f(8, 8, 8), atenuation);
        Light light8 = new Light(new Vector3f(0, 0, 0), new Vector3f(8, 8, 8), atenuation);
        Light light9 = new Light(new Vector3f(0, 0, 0), new Vector3f(20, 15, 15), atenuation);
        entities.add(new Entity(staticLampModel, new Vector3f(190, 0, 500), 0, 0, 0, 1, light, 6));
        entities.add(new Entity(staticLampModel, new Vector3f(200, 0, 1000), 0, 0, 0, 1, light2, 6));
        entities.add(new Entity(staticLampModel, new Vector3f(280, 0, 510), 0, 0, 0, 1, light3, 6));
        entities.add(new Entity(staticLampModel, new Vector3f(500, 0, 670), 0, 0, 0, 1, light4, 6));
        entities.add(new Entity(staticLampModel, new Vector3f(818, 0, 72), 0, 0, 0, 1, light5, 6));
        entities.add(new Entity(staticLampModel, new Vector3f(1353, 0, 1345), 0, 0, 0, 1, light6, 6));
        entities.add(new Entity(staticLampModel, new Vector3f(1079, 0, 746), 0, 0, 0, 1, light7, 6));
        entities.add(new Entity(staticLampModel, new Vector3f(1393, 0, 301), 0, 0, 0, 1, light8, 6));
        entities.add(new Entity(staticLampModel, new Vector3f(867, 0, 1172), 0, 0, 0, 1, light9, 6));
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
