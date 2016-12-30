package pl.nikowis.shaders.naked;

/**
 * Class for presenting Gouraud and Phong shading models.
 * Created by Nikodem on 12/30/2016.
 */
public class NonFlatShader extends NakedShader {

    private static final String VERTEX_FILE = "src\\pl\\nikowis\\shaders\\naked\\nakedVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src\\pl\\nikowis\\shaders\\naked\\nonflatFragmentShader.glsl";

    public NonFlatShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

}