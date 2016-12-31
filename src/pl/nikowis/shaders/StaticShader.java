package pl.nikowis.shaders;

/**
 * Implementation of {@link ShaderProgram}.
 * Used to manipulate uniform variables.
 * Shader for any object but terrain.
 * Created by Nikodem on 12/24/2016.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/pl/nikowis/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "/pl/nikowis/shaders/fragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }
}
