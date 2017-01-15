package pl.nikowis.engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.config.Config;
import pl.nikowis.entities.CameraManager;
import pl.nikowis.entities.CarEntity;
import pl.nikowis.entities.Entity;
import pl.nikowis.entities.Light;
import pl.nikowis.entities.MovingEntity;
import pl.nikowis.entities.StaticCamera;
import pl.nikowis.entities.ThirdPersonCamera;
import pl.nikowis.entities.TurningCamera;
import pl.nikowis.models.FullModel;
import pl.nikowis.models.RawModel;
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

/**
 * Main class.
 * Created by Nikodem on 12/22/2016.
 */
public class MainGameLoop {

    private static float atBaseFact = 1;
    private static float atDistFact = 0f;
    private static float atDistSquareFact = 0.001f;
    private static Vector3f atenuation = new Vector3f(atBaseFact, atDistFact, atDistSquareFact);

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //###############################   MODELS  ##########################
        RawModel rawLampModel = OBJLoader.loadObjModel("lamp", loader);
        FullModel staticLampModel = new FullModel(rawLampModel, new ModelTexture(loader.loadTexture("lamp")), new Vector3f(0.2f, 0.2f, 0.2f));

        RawModel rawStallModel = OBJLoader.loadObjModel("stall", loader);
        FullModel staticStallModel = new FullModel(rawStallModel, new ModelTexture(loader.loadTexture("stallTexture")), new Vector3f(0.5f, 0.2f, 0.2f));
        staticStallModel.setShineDamper(100);
        staticStallModel.setReflectivity(1);

        RawModel rawBuildingsModel = OBJLoader.loadObjModel("building", loader);
        FullModel staticBuildingsModel = new FullModel(rawBuildingsModel, new ModelTexture(loader.loadTexture("buildingsTexture")), new Vector3f(1, 1, 0));
        staticStallModel.setShineDamper(100);
        staticStallModel.setReflectivity(1);

        RawModel planeModel = OBJLoader.loadObjModel("plane", loader);
        FullModel staticPlaneModel = new FullModel(planeModel, new ModelTexture(loader.loadTexture("buildingsTexture")), new Vector3f(0.5f, 0.2f, 0.2f));
        staticStallModel.setShineDamper(100);
        staticStallModel.setReflectivity(1);

        RawModel rawFarmhouseModel = OBJLoader.loadObjModel("farmhouse", loader);
        FullModel staticFarmhouseModel = new FullModel(rawFarmhouseModel, new ModelTexture(loader.loadTexture("farmhouseTexture")), new Vector3f(0.1f, 0.2f, 0.7f));
        staticStallModel.setShineDamper(100);
        staticStallModel.setReflectivity(1);

        RawModel rawBoxModel = OBJLoader.loadObjModel("box", loader);
        FullModel staticBoxModel = new FullModel(rawBoxModel, new ModelTexture(loader.loadTexture("box")), new Vector3f(0.62f, 0.32f, 0.176f));
        staticBoxModel.setShineDamper(100);
        staticBoxModel.setReflectivity(1);

        RawModel rawDragonModel = OBJLoader.loadObjModel("dragon", loader);
        FullModel staticDragonModel = new FullModel(rawDragonModel, new ModelTexture(loader.loadTexture("camaroTexture")), new Vector3f(0.9f, 0.9f, 0.9f));
        staticDragonModel.setShineDamper(100);
        staticDragonModel.setReflectivity(1);

        RawModel rawBunnyModel = OBJLoader.loadObjModel("bunny", loader);
        FullModel staticBunnyModel = new FullModel(rawBunnyModel, new ModelTexture(loader.loadTexture("camaroTexture")), new Vector3f(0.9f, 0.9f, 0.9f));
        staticDragonModel.setShineDamper(100);
        staticDragonModel.setReflectivity(1);

        RawModel rawTreeModel = OBJLoader.loadObjModel("tree", loader);
        FullModel staticTreeModel = new FullModel(rawTreeModel, new ModelTexture(loader.loadTexture("tree")), new Vector3f(0.3f, 1f, 0.3f));
        staticTreeModel.setShineDamper(200);
        staticTreeModel.setReflectivity(1);

        RawModel rawCarModel = OBJLoader.loadObjModel("camaro", loader);
        FullModel staticCarModel = new FullModel(rawCarModel, new ModelTexture(loader.loadTexture("camaroTexture")), new Vector3f(1, 1, 1));
        staticCarModel.setShineDamper(100);
        staticCarModel.setReflectivity(1);

        RawModel rawPersonModel = OBJLoader.loadObjModel("person", loader);
        FullModel staticPersonModel = new FullModel(rawPersonModel, new ModelTexture(loader.loadTexture("playerTexture")), new Vector3f(1, 1, 1));
        staticPersonModel.setShineDamper(100);
        staticPersonModel.setReflectivity(1);
        //####################################################################

        //###############################   ENTITIES  ########################
        List<Entity> entities = new ArrayList<Entity>();
        createTreeOutline(staticTreeModel, entities);
        Entity stallEntity = new Entity(staticStallModel, new Vector3f(120, 0, 95), 0, 0, 0, 4);
        Entity stallEntity2 = new Entity(staticStallModel, new Vector3f(698, 0, 156), 0, 70, 0, 3);
        Entity boxEntity = new Entity(staticBoxModel, new Vector3f(696, 3, 183), 0, 33, 0, 3);
        Entity boxEntity2 = new Entity(staticBoxModel, new Vector3f(699, 3, 199), 0, 45, 0, 3);
        Entity boxEntity3 = new Entity(staticBoxModel, new Vector3f(110, 7, 140), 0, 15, 0, 6);
        Entity boxEntity4 = new Entity(staticBoxModel, new Vector3f(110, 5, 170), 0, 70, 0, 4);
        Entity personEntity = new Entity(staticPersonModel, new Vector3f(118, 0, 80), 0, 0, 0, 1);
        Entity personEntity2 = new Entity(staticPersonModel, new Vector3f(120, 0, 103), 0, 180, 0, 1);
        Entity personEntity3 = new Entity(staticPersonModel, new Vector3f(705, 0, 158), 0, 240, 0, 1);

        Entity dragonEntity = new Entity(staticDragonModel, new Vector3f(615, 0, 606), 0, 240, 0, 5);
        Entity bunnyEntity = new Entity(staticBunnyModel, new Vector3f(176, 0, 642), 0, 240, 0, 5);
        Entity buildingsEntity = new Entity(staticBuildingsModel, new Vector3f(330, 0, 490), 270, 0, 0, 0.04f);
        Entity farmhouseEntity = new Entity(staticFarmhouseModel, new Vector3f(230, 0, 260), 0, 80, 0, 2);
        Entity planeEntity = new Entity(staticPlaneModel, new Vector3f(430, 50, 200), 220, 80, 140, 5);

        List<Light> carLights = new ArrayList<>();
        carLights.add(new Light(new Vector3f(-4, 3, 13), new Vector3f(3, 3, 3), atenuation));
        carLights.add(new Light(new Vector3f(4, 3, 13), new Vector3f(3, 3, 3), atenuation));

        CarEntity carEntity = new CarEntity(staticCarModel, new Vector3f(240, 0, 450), 0, 0, 0, 2, carLights, 3);

        entities.add(stallEntity);
        entities.add(stallEntity2);
        entities.add(carEntity);
        entities.add(boxEntity);
        entities.add(boxEntity2);
        entities.add(boxEntity3);
        entities.add(boxEntity4);
        entities.add(personEntity);
        entities.add(personEntity2);
        entities.add(personEntity3);
        entities.add(dragonEntity);
        entities.add(bunnyEntity);
        entities.add(buildingsEntity);
        entities.add(farmhouseEntity);
        entities.add(planeEntity);

        List<Light> lights = new ArrayList<>();
        setupLampsAndSun(staticLampModel, entities, lights);
        //####################################################################

        //###############################   TERRAIN  #########################
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("specular"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, new Vector3f(0.3f, 0.3f, 0.3f));
        //####################################################################

        //###############################   CAMERAS  #########################
        StaticCamera staticCamera = new StaticCamera(new Vector3f(0, 50, 0), 10);
        staticCamera.setYaw(130);
        ThirdPersonCamera thirdPersonCamera = new ThirdPersonCamera(carEntity);
        TurningCamera turningCamera = new TurningCamera(new Vector3f(0, 50, 0), 10, carEntity);
        CameraManager cameraManager = new CameraManager(staticCamera, thirdPersonCamera, turningCamera);
        //####################################################################

        MasterRenderer masterRenderer = new MasterRenderer();
        masterRenderer.processTerrain(terrain);
        for (Entity entity : entities) {
            masterRenderer.processEntity(entity);
        }

        while (!Display.isCloseRequested()) {
            cameraManager.checkInput();
            cameraManager.moveCurrentCamera();
            carEntity.move();
            masterRenderer.checkInput();
            masterRenderer.render(lights, cameraManager.getCurrentCamera());
            DisplayManager.updateDisplay();
        }

        masterRenderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static void setupLampsAndSun(FullModel staticLampModel, List<Entity> entities, List<Light> lights) {
        Light sunLight = new Light(new Vector3f(800, 10000, 800), new Vector3f(0.4f, 0.4f, 0.4f));
        createLamps(staticLampModel, entities);
        lights.add(sunLight);
        for (int i = 0; i < entities.size(); i++) {
            Entity en = entities.get(i);
            if (en.getLights() != null) {
                lights.addAll(en.getLights());
            }
        }
    }

    private static void createLamps(FullModel staticLampModel, List<Entity> entities) {
        Light light = new Light(new Vector3f(0, 6, 0), new Vector3f(8, 8, 8), atenuation);
        Light light3 = new Light(new Vector3f(0, 6, 0), new Vector3f(0, 0, 15), atenuation);
        Light light4 = new Light(new Vector3f(0, 6, 0), new Vector3f(8, 0, 0), atenuation);
        Light light5 = new Light(new Vector3f(0, 6, 0), new Vector3f(8, 8, 8), atenuation);
        Light light6 = new Light(new Vector3f(0, 6, 0), new Vector3f(2, 8, 2), atenuation);
        entities.add(new Entity(staticLampModel, new Vector3f(190, 0, 500), 0, 0, 0, 1, light));
        entities.add(new Entity(staticLampModel, new Vector3f(280, 0, 510), 0, 0, 0, 1, light3));
        entities.add(new Entity(staticLampModel, new Vector3f(500, 0, 670), 0, 0, 0, 1, light4));
        entities.add(new Entity(staticLampModel, new Vector3f(400, 0, 72), 0, 0, 0, 1, light5));
        entities.add(new Entity(staticLampModel, new Vector3f(117, 0, 67), 0, 0, 0, 1, light6));
    }

    private static void createTreeOutline(FullModel staticTreeModel, List<Entity> entities) {
        for (int i = 0; i < Config.TERRAIN_SIZE; i += Config.TREE_OUTLINE_STEP) {
            entities.add(new Entity(staticTreeModel, new Vector3f(0, 0, i), 0, 0, 0, 7));
        }
        for (int i = 0; i < Config.TERRAIN_SIZE; i += Config.TREE_OUTLINE_STEP) {
            entities.add(new Entity(staticTreeModel, new Vector3f(i, 0, 0), 0, 0, 0, 7));
        }
        for (int i = 0; i < Config.TERRAIN_SIZE; i += Config.TREE_OUTLINE_STEP) {
            entities.add(new Entity(staticTreeModel, new Vector3f(Config.TERRAIN_SIZE, 0, i), 0, 0, 0, 7));
        }
        for (int i = 0; i < Config.TERRAIN_SIZE; i += Config.TREE_OUTLINE_STEP) {
            entities.add(new Entity(staticTreeModel, new Vector3f(i, 0, Config.TERRAIN_SIZE), 0, 0, 0, 7));
        }
    }

}
