package pl.nikowis;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.Camera;
import pl.nikowis.entities.CameraManager;
import pl.nikowis.entities.Entity;
import pl.nikowis.entities.Light;
import pl.nikowis.entities.MovingCamera;
import pl.nikowis.entities.StaticCamera;
import pl.nikowis.renderEngine.GuiRenderer;
import pl.nikowis.shaders.particles.AsteroidsBeltGenerator;
import pl.nikowis.shaders.particles.ParticleMaster;
import pl.nikowis.shaders.particles.ParticleGenerator;
import pl.nikowis.shaders.particles.ParticleTexture;
import pl.nikowis.textures.GuiTexture;
import pl.nikowis.models.FullModel;
import pl.nikowis.renderEngine.DisplayManager;
import pl.nikowis.renderEngine.Loader;
import pl.nikowis.renderEngine.MasterRenderer;
import pl.nikowis.renderEngine.OBJLoader;
import pl.nikowis.textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class.
 * Created by Nikodem on 12/22/2016.
 */
public class MainGameLoop {

    private static float atBaseFact = 1;
    private static float atDistFact = 0f;
    private static float atDistSquareFact = 0.0001f;
    private static Vector3f atenuation = new Vector3f(atBaseFact, atDistFact, atDistSquareFact);

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //###############################   MODELS  ##########################
        FullModel sphereModel = new FullModel(OBJLoader.loadObjModel("sphere", loader), new ModelTexture(loader.loadTexture("rock")), new Vector3f(0.82f, 0.82f, 0.82f));
        FullModel iglooModel = new FullModel(OBJLoader.loadObjModel("igloo", loader), new ModelTexture(loader.loadTexture("rock")), new Vector3f(0.22f, 0.2f, 0.9f));
        FullModel satelliteModel = new FullModel(OBJLoader.loadObjModel("satellite", loader), new ModelTexture(loader.loadTexture("rock")), new Vector3f(0.82f, 0.12f, 0.1f));
        FullModel boxModel = new FullModel(OBJLoader.loadObjModel("box", loader), new ModelTexture(loader.loadTexture("box")), new Vector3f(0.62f, 0.32f, 0.176f));
        FullModel treeModel = new FullModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")), new Vector3f(0.3f, 1f, 0.3f));
        //####################################################################

        //###############################   GUIS  ##########################
        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        guis.add(gui);
        //####################################################################

        //###############################   ENTITIES  ########################
        List<Entity> entities = new ArrayList<>();
        Light light = new Light(new Vector3f(0, 0, 0), new Vector3f(0.1f, 0.1f, 0.9f), atenuation);
        light.setConeAngle(20);
        light.setConeDirection(new Vector3f(0.2f, 0.2f, 1f));
        Entity sphereEntity = new Entity(sphereModel, new Vector3f(-130, -100, 480), 0, 0, 0, 200);
        Entity satelliteEntity = new Entity(satelliteModel, new Vector3f(0, -20, 250), 0, 50, 50, 30, light);
        Light light2 = new Light(new Vector3f(0, 0, 0), new Vector3f(0.1f, 0.8f, 0.1f), atenuation);
        light2.setConeAngle(20);
        light2.setConeDirection(new Vector3f(0.7f, 0.2f, 1f));
        Entity satelliteEntity2 = new Entity(satelliteModel, new Vector3f(-100, 0, 250), 0, 90, 50, 30, light2);
        Entity iglooEntity = new Entity(iglooModel, new Vector3f(-77, 3, 370), -50, 300, 50, 30);
        Entity boxEntity = new Entity(boxModel, new Vector3f(10, 20, 325), 0, 90, 0, 10);
        Entity treeEntity = new Entity(treeModel, new Vector3f(0, 170, 400), 0, 0, 0, 20);
        Entity treeEntity2 = new Entity(treeModel, new Vector3f(70, 170, 410), 0, 0, 0, 20);
        Entity treeEntity3 = new Entity(treeModel, new Vector3f(-30, 200, 450), 0, 0, 0, 20);
        entities.add(sphereEntity);
        entities.add(satelliteEntity);
        entities.add(satelliteEntity2);
        entities.add(iglooEntity);
        entities.add(boxEntity);
        entities.add(treeEntity);
        entities.add(treeEntity2);
        entities.add(treeEntity3);
        Light sunLight = new Light(new Vector3f(800, 10000, 800), new Vector3f(0.8f, 0.8f, 0.8f));
        List<Light> lights = setupLights(entities, sunLight);
        //####################################################################

        //###############################   CAMERAS  #########################
        StaticCamera staticCamera = new StaticCamera(new Vector3f(300, 200, 0), 10);
        staticCamera.setYaw(1300);
        MovingCamera movingCamera = new MovingCamera(new Vector3f(100, 150, 0));
        movingCamera.setPitch(10);
        CameraManager cameraManager = new CameraManager(movingCamera, staticCamera);
        //####################################################################

        MasterRenderer masterRenderer = new MasterRenderer(loader);
        ParticleMaster.init(loader, masterRenderer.getProjectionMatrix());
        GuiRenderer guiRenderer = new GuiRenderer(loader);

        ParticleTexture starTexture = new ParticleTexture(loader.loadTexture("asteroid"), 1);
        ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("fire"), 4);
        ParticleGenerator particleGenerator = new ParticleGenerator(particleTexture, 14, 25, 1, 2);
        AsteroidsBeltGenerator asteroidsBeltGenerator = new AsteroidsBeltGenerator(starTexture);
        asteroidsBeltGenerator.generateAsteroids(new Vector3f(0, -300, 400), 50);

        for (Entity entity : entities) {
            masterRenderer.processEntity(entity);
        }

        while (!Display.isCloseRequested()) {
            cameraManager.checkInput();
            cameraManager.moveCurrentCamera();

            particleGenerator.generateParticles(new Vector3f(0, 250, 500));
            ParticleMaster.update(cameraManager.getCurrentCamera());

            masterRenderer.checkInput();
            Camera camera = cameraManager.getCurrentCamera();
            masterRenderer.render(lights, camera);
            ParticleMaster.renderParticles(camera);

            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }

        ParticleMaster.cleanUp();
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
