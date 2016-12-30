package pl.nikowis.shaders;

import org.lwjgl.util.vector.Vector3f;

/**
 * Shader for entities without textures for presenting different shading and reflection models.
 * Created by Nikodem on 12/30/2016.
 */
public class NakedShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src\\pl\\nikowis\\shaders\\nakedVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src\\pl\\nikowis\\shaders\\nakedFragmentShader.glsl";
    private static final String GEOMETRY_FILE = "src\\pl\\nikowis\\shaders\\geometryShader.glsl";

    protected int location_baseColour;

    public NakedShader() {
        super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
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
