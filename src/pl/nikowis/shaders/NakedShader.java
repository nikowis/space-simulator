package pl.nikowis.shaders;

/**
 * Shader for entities without textures for presenting different shading and reflection models.
 * Created by Nikodem on 12/30/2016.
 */
public class NakedShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src\\pl\\nikowis\\shaders\\nakedVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src\\pl\\nikowis\\shaders\\nakedFragmentShader.glsl";
    private static final String GEOMETRY_FILE = "src\\pl\\nikowis\\shaders\\geometryShader.glsl";

    public NakedShader() {
        super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

}
