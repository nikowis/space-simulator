package pl.nikowis.renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import pl.nikowis.entities.Entity;
import pl.nikowis.models.RawModel;
import pl.nikowis.models.TexturedModel;
import pl.nikowis.shaders.StaticShader;
import pl.nikowis.textures.ModelTexture;
import pl.nikowis.toolbox.Maths;

import java.util.List;
import java.util.Map;

/**
 * Renderer for entities. This class optimizes and enables rendering of the
 * same TexturedModel multiple times.
 * Created by Nikodem on 12/23/2016.
 */
public class EntityRenderer {

    private StaticShader shader;

    /**
     * Constructor.
     * @param shader static shader
     * @param projectionMatrix projection matrix
     */
    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.start();
    }

    /**
     * Renders all the entities with associated texturedModels.
     * @param entities map of textured models and entities.
     */
    public void render(Map<TexturedModel, List<Entity>> entities) {
        for(TexturedModel model : entities.keySet()) {
            prepareTexutredModel(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexutredModel();
        }
    }

    private void prepareTexutredModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        ModelTexture texture = model.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
    }

    private void unbindTexutredModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale()
        );
        shader.loadTransformationMatrix(transformationMatrix);
    }

}
