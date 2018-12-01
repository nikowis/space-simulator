package pl.nikowis.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import pl.nikowis.entities.Entity;
import pl.nikowis.models.RawModel;
import pl.nikowis.models.FullModel;
import pl.nikowis.shaders.statik.StaticShader;
import pl.nikowis.textures.ModelTexture;
import pl.nikowis.toolbox.Maths;

import java.util.List;
import java.util.Map;

/**
 * Renderer for entities. This class optimizes and enables rendering of the
 * same FullModel multiple times.
 * Created by Nikodem on 12/23/2016.
 */
public class EntityRenderer {

    private StaticShader shader;
    private CubeMap environmentMap;

    /**
     * Constructor.
     * @param shader static shader
     * @param projectionMatrix projection matrix
     */
    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix, CubeMap environmentMap) {
        this.shader = shader;
        this.environmentMap = environmentMap;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }

    /**
     * Renders all the entities with associated texturedModels.
     * @param entities map of textured models and entities.
     */
    public void render(Map<FullModel, List<Entity>> entities) {
        for(FullModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            bindEnvironmentMap();
            for(Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexutredModel();
        }
    }

    private void prepareTexturedModel(FullModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        ModelTexture texture = model.getTexture();
        shader.loadNumberOfRows(texture.getNumberOfRows());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity(), model.getCubeMapReflection());
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
        shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
    }

    private void bindEnvironmentMap(){
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, environmentMap.getTexture());
    }

}
