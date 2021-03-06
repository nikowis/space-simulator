package pl.nikowis.textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import pl.nikowis.renderEngine.Loader;

/**
 * Created by Nikodem on 12/1/2018.
 */
public class EnvironmentMapTexture {

    public final int textureId;
    public final int size;
    private final int type;

    protected EnvironmentMapTexture(int textureId, int size) {
        this.textureId = textureId;
        this.size = size;
        this.type = GL11.GL_TEXTURE_2D;
    }

    protected EnvironmentMapTexture(int textureId, int type, int size) {
        this.textureId = textureId;
        this.size = size;
        this.type = type;
    }

    public void bindToUnit(int unit) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
        GL11.glBindTexture(type, textureId);
    }

    public void delete() {
        GL11.glDeleteTextures(textureId);
    }

    public static TextureBuilder newTexture(String textureFile) {
        return new TextureBuilder(textureFile);
    }

    public static EnvironmentMapTexture newCubeMap(String[] textureFiles, Loader loader) {
        int cubeMapId = loader.loadCubeMap(textureFiles);
        return new EnvironmentMapTexture(cubeMapId, GL13.GL_TEXTURE_CUBE_MAP, 0);
    }

    public static EnvironmentMapTexture newEmptyCubeMap(int size, Loader loader) {
        int cubeMapId = loader.createEmptyCubeMap(size);
        return new EnvironmentMapTexture(cubeMapId, GL13.GL_TEXTURE_CUBE_MAP, size);
    }

}
