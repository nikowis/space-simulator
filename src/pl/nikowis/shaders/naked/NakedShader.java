package pl.nikowis.shaders.naked;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.shaders.ShaderProgram;

/**
 * Shader for entities without textures for presenting different shading and reflection models.
 * Created by Nikodem on 12/30/2016.
 */
public abstract class NakedShader extends ShaderProgram {

    protected int location_baseColour;

    public NakedShader(String vertexFile, String fragmentFile, String geometryFile) {
        super(vertexFile, fragmentFile, geometryFile);
    }

    public NakedShader(String vertexFile, String fragmentFile) {
        super(vertexFile, fragmentFile);
    }

    public void loadBaseColour(Vector3f baseColour) {
        loadVector(location_baseColour, baseColour);
    }

    @Override
    protected void getAllUniformLocations() {
        super.getAllUniformLocations();
        location_baseColour = getUniformLocation("baseColour");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }
}
