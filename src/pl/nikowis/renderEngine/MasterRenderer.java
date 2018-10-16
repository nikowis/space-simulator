package pl.nikowis.renderEngine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import pl.nikowis.config.Config;
import pl.nikowis.entities.Camera;
import pl.nikowis.entities.Entity;
import pl.nikowis.entities.Light;
import pl.nikowis.models.FullModel;
import pl.nikowis.shaders.statik.StaticShader;
import pl.nikowis.shaders.terrain.TerrainShader;
import pl.nikowis.shaders.naked.FlatShader;
import pl.nikowis.shaders.naked.NonFlatShader;
import pl.nikowis.terrains.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rendering class(controls entities and terrain rendering).
 * Created by Nikodem on 12/25/2016.
 */
public class MasterRenderer {

    /**
     * Controls rendering mode ( naked or textured ).
     */
    private boolean nakedMode = true;
    private boolean phongShadingModel = true;
    private boolean gouraudShadingModel = false;
    private boolean phongReflectionModel = true;
    private boolean blinnReflectionModel = false;

    private Matrix4f projectionMatrix;

    private EntityRenderer entityRenderer;
    private TerrainRenderer terrainRenderer;
    private NakedRenderer nakedRenderer;
    private StaticShader staticShader = new StaticShader();
    private TerrainShader terrainShader = new TerrainShader();
    private NonFlatShader nonFlatShader = new NonFlatShader();
    private FlatShader flatShader = new FlatShader();

    private Map<FullModel, List<Entity>> entities = new HashMap<FullModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(staticShader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        nakedRenderer = new NakedRenderer(nonFlatShader, projectionMatrix);
        nonFlatShader.loadPhongGouardShading(true, false);
        nonFlatShader.loadPhongBlinnReflection(true, false);
    }

    /**
     * Renders all processed entities in each frame.
     *
     * @param lights lights to factor in
     * @param camera thirdPersonCamera which watches the scene
     */
    public void render(List<Light> lights, Camera camera) {
        prepare();
        if (!nakedMode) {
            staticShader.start();
            staticShader.loadLights(lights);
            staticShader.loadViewMatrix(camera);
            entityRenderer.render(entities);
            staticShader.stop();
            terrainShader.start();
            terrainShader.loadLights(lights);
            terrainShader.loadViewMatrix(camera);
            terrainRenderer.render(terrains);
            terrainShader.stop();
        } else {
            nakedRenderer.getShader().start();
            nakedRenderer.getShader().loadLights(lights);
            nakedRenderer.getShader().loadViewMatrix(camera);
            nakedRenderer.getShader().loadPhongGouardShading(phongShadingModel, gouraudShadingModel);
            nakedRenderer.getShader().loadPhongBlinnReflection(phongReflectionModel, blinnReflectionModel);
            nakedRenderer.render(entities);
            nakedRenderer.render(terrains);
            nakedRenderer.getShader().stop();
        }
    }

    /**
     * Adds a terrain to render.
     *
     * @param terrain terrain to process
     */
    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    /**
     * Adds an entity to render.
     *
     * @param entity entity to process
     */
    public void processEntity(Entity entity) {
        FullModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    /**
     * Cleans up internals.
     */
    public void cleanUp() {
        staticShader.cleanUp();
        terrainShader.cleanUp();
        nonFlatShader.cleanUp();
        flatShader.cleanUp();
    }


    private void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.8f, 1, 1, 1);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(Config.FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = Config.FAR_PLANE - Config.NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((Config.FAR_PLANE + Config.NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * Config.NEAR_PLANE * Config.FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    /**
     * Checks if the rendering mode should be changed.
     */
    public void checkInput() {

        if (Keyboard.isKeyDown(Keyboard.KEY_N)) {
            if (!nakedMode) {
                nakedMode = true;
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
            if (nakedMode) {
                nakedMode = false;
            }
        }
        if (nakedMode) {
            if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
                nakedRenderer = new NakedRenderer(flatShader, projectionMatrix);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                phongShadingModel = false;
                gouraudShadingModel = true;
                nakedRenderer = new NakedRenderer(nonFlatShader, projectionMatrix);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
                phongShadingModel = true;
                gouraudShadingModel = false;
                nakedRenderer = new NakedRenderer(nonFlatShader, projectionMatrix);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
                phongReflectionModel = true;
                blinnReflectionModel = false;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_O)) {
                phongReflectionModel = false;
                blinnReflectionModel = true;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
                phongReflectionModel = false;
                blinnReflectionModel = false;
            }
        }
    }
}
