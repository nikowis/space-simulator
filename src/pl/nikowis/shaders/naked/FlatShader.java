package pl.nikowis.shaders.naked;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.shaders.ShaderProgram;

/**
 *  Flat shader.
 * Created by Nikodem on 12/30/2016.
 */
public class FlatShader extends NakedShader {

    private static final String VERTEX_FILE = "src\\pl\\nikowis\\shaders\\naked\\nakedVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src\\pl\\nikowis\\shaders\\naked\\flatFragmentShader.glsl";
    private static final String GEOMETRY_FILE = "src\\pl\\nikowis\\shaders\\naked\\flatGeometryShader.glsl";

    public FlatShader() {
        super(VERTEX_FILE, FRAGMENT_FILE, GEOMETRY_FILE);
    }

}
