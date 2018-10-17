package pl.nikowis.shaders.naked;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.shaders.ShaderProgram;

/**
 * Shader for entities without textures for presenting different shading and reflection models.
 * Created by Nikodem on 12/30/2016.
 */
public class NakedShader extends ShaderProgram {

    protected int location_baseColour;

    private static final String VERTEX_FILE = "/pl/nikowis/shaders/naked/nakedVertexShader.glsl";
    private static final String FRAGMENT_FILE = "/pl/nikowis/shaders/naked/nakedFragmentShader.glsl";

    public NakedShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
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
