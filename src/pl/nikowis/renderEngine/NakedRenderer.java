package pl.nikowis.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.Entity;
import pl.nikowis.models.RawModel;
import pl.nikowis.models.TexturedUntexturedModel;
import pl.nikowis.shaders.NakedShader;
import pl.nikowis.terrains.Terrain;
import pl.nikowis.toolbox.Maths;

import java.util.List;
import java.util.Map;

/**
 * Renderer for untextured models.
 * Created by Nikodem on 12/30/2016.
 */
public class NakedRenderer {

    private NakedShader shader;

    /**
     * Constructor.
     *
     * @param shader           static shader
     * @param projectionMatrix projection matrix
     */
    public NakedRenderer(NakedShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    /**
     * Renders all the entities with associated info.
     *
     * @param entities map of textured models and entities.
     */
    public void render(Map<TexturedUntexturedModel, List<Entity>> entities) {
        for (TexturedUntexturedModel model : entities.keySet()) {
            prepareModel(model.getRawModel(), model.getDefaultColour(), model.getShineDamper(), model.getReflectivity());
            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                loadTransformationMatrix(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindModel();
        }
    }

    public void render(List<Terrain> terrains) {
        for (Terrain terrain : terrains) {
            prepareModel(terrain.getModel(), terrain.getDefaultColour(), terrain.getShineDamper(), terrain.getReflectivity());
            loadTransformationMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindModel();
        }
    }

    private void prepareModel(RawModel rawModel, Vector3f baseColour, float shineDamper, float reflectivity) {
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.loadShineVariables(shineDamper, reflectivity);
        shader.loadBaseColour(baseColour);
    }

    private void unbindModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadTransformationMatrix(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()
        );
        shader.loadTransformationMatrix(transformationMatrix);
    }

    private void loadTransformationMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1
        );
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
