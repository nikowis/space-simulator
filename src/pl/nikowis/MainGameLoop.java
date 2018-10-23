package pl.nikowis;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.CameraManager;
import pl.nikowis.entities.Entity;
import pl.nikowis.entities.Light;
import pl.nikowis.entities.MovingCamera;
import pl.nikowis.entities.StaticCamera;
import pl.nikowis.models.FullModel;
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

    private static final Vector3f[] SUN_COLORS = new Vector3f[] {
            new Vector3f(0.4f,0,0),
            new Vector3f(0,0.4f,0),
            new Vector3f(0,0,0.4f),
            new Vector3f(0.4f, 0.4f, 0.4f),
    };
    private static float atBaseFact = 1;
    private static float atDistFact = 0f;
    private static float atDistSquareFact = 0.0001f;
    private static Vector3f atenuation = new Vector3f(atBaseFact, atDistFact, atDistSquareFact);

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //###############################   MODELS  ##########################
        FullModel staticSphereModel = new FullModel(OBJLoader.loadObjModel("sphere", loader), new ModelTexture(loader.loadTexture("rock")), new Vector3f(0.82f, 0.82f, 0.82f));
        FullModel satelliteModel = new FullModel(OBJLoader.loadObjModel("satellite", loader), new ModelTexture(loader.loadTexture("rock")), new Vector3f(0.82f, 0.12f, 0.1f));
        //####################################################################

        //###############################   ENTITIES  ########################
        List<Entity> entities = new ArrayList<>();
        Light light = new Light(new Vector3f(0, 0, 0), new Vector3f(0.1f, 0.1f, 0.9f), atenuation);
        light.setConeAngle(20);
        light.setConeDirection(new Vector3f(0.2f, 0.2f,1f));
        Entity sphereEntity = new Entity(staticSphereModel, new Vector3f(0, 0, 500), 0, 0, 0, 200);
        Entity satelliteEntity = new Entity(satelliteModel, new Vector3f(0, 80, 300), 0, 50, 50, 30, light);
        Light light2 = new Light(new Vector3f(0, 0, 0), new Vector3f(0.1f, 0.8f, 0.1f), atenuation);
        light2.setConeAngle(20);
        light2.setConeDirection(new Vector3f(0.7f, 0.2f,1f));
        Entity satelliteEntity2 = new Entity(satelliteModel, new Vector3f(-100, 100, 300), 0, 90, 50, 30, light2);
        entities.add(sphereEntity);
        entities.add(satelliteEntity);
        entities.add(satelliteEntity2);
        Light sunLight = new Light(new Vector3f(800, 10000, 800), new Vector3f(0.4f, 0.4f, 0.4f));
        List<Light> lights = setupLights(entities, sunLight);
        //####################################################################

        //###############################   TERRAIN  #########################
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("rock"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, new Vector3f(0.8f, 0.8f, 0.8f));
        //####################################################################

        //###############################   CAMERAS  #########################
        StaticCamera staticCamera = new StaticCamera(new Vector3f(0, 0, 0), 10);
        staticCamera.setYaw(130);
        MovingCamera movingCamera = new MovingCamera(new Vector3f(0, 150, 0), new Vector3f(0, 0, 0));
        movingCamera.setPitch(10);
        movingCamera.setRoll(110);
        CameraManager cameraManager = new CameraManager(staticCamera, movingCamera, null);
        //####################################################################

        MasterRenderer masterRenderer = new MasterRenderer();
        //masterRenderer.processTerrain(terrain);
        for (Entity entity : entities) {
            masterRenderer.processEntity(entity);
        }

        int i = 0;
        int j = 0;
        while (!Display.isCloseRequested()) {
            i++;
            if(i % 150 == 0) {
                j++;
                sunLight.setColour(SUN_COLORS[j % SUN_COLORS.length]);
            }
            cameraManager.checkInput();
            cameraManager.moveCurrentCamera();
            masterRenderer.checkInput();
            masterRenderer.render(lights, cameraManager.getCurrentCamera());
            DisplayManager.updateDisplay();
        }

        masterRenderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    private static List<Light> setupLights(List<Entity> entities, Light sunLight) {
        List<Light> lights = new ArrayList<>();

        lights.add(sunLight);
        for (int i = 0; i < entities.size(); i++) {
            Entity en = entities.get(i);
            if (en.getLights() != null) {
                lights.addAll(en.getLights());
            }
        }
        return lights;
    }
}
