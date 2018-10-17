package pl.nikowis;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.CameraManager;
import pl.nikowis.entities.Entity;
import pl.nikowis.entities.Light;
import pl.nikowis.entities.MovingEntity;
import pl.nikowis.entities.StaticCamera;
import pl.nikowis.entities.ThirdPersonCamera;
import pl.nikowis.entities.TurningCamera;
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

    private static float atBaseFact = 1;
    private static float atDistFact = 0f;
    private static float atDistSquareFact = 0.001f;
    private static Vector3f atenuation = new Vector3f(atBaseFact, atDistFact, atDistSquareFact);

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //###############################   MODELS  ##########################
        FullModel staticBoxModel = new FullModel(OBJLoader.loadObjModel("box", loader), new ModelTexture(loader.loadTexture("box")), new Vector3f(0.62f, 0.32f, 0.176f));
        FullModel staticSphereModel = new FullModel(OBJLoader.loadObjModel("sphere", loader), new ModelTexture(loader.loadTexture("rock")), new Vector3f(0.82f, 0.82f, 0.82f));
        //####################################################################

        //###############################   ENTITIES  ########################
        List<Entity> entities = new ArrayList<>();
        MovingEntity boxEntity = new MovingEntity(staticBoxModel, new Vector3f(100, 3, 100), 0, 33, 0, 3);
        entities.add(boxEntity);
        entities.add(new Entity(staticSphereModel, new Vector3f(200, 20, 200), 0, 33, 0, 10));
        List<Light> lights = new ArrayList<>();
        Light sunLight = new Light(new Vector3f(800, 10000, 800), new Vector3f(0.4f, 0.4f, 0.4f));
        lights.add(sunLight);
        //####################################################################

        //###############################   TERRAIN  #########################
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("rock"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, new Vector3f(0.3f, 0.3f, 0.3f));
        //####################################################################

        //###############################   CAMERAS  #########################
        StaticCamera staticCamera = new StaticCamera(new Vector3f(0, 50, 0), 10);
        staticCamera.setYaw(130);
        ThirdPersonCamera thirdPersonCamera = new ThirdPersonCamera(boxEntity);
        TurningCamera turningCamera = new TurningCamera(new Vector3f(0, 50, 0), 10, boxEntity);
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
            boxEntity.move();
            masterRenderer.checkInput();
            masterRenderer.render(lights, cameraManager.getCurrentCamera());
            DisplayManager.updateDisplay();
        }

        masterRenderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
