package pl.nikowis.shaders;

/**
 * Created by Nikodem on 12/24/2016.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src\\pl\\nikowis\\shaders\\vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src\\pl\\nikowis\\shaders\\fragmentShader.glsl";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
