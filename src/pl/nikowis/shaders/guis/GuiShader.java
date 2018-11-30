package pl.nikowis.shaders.guis;

import org.lwjgl.util.vector.Matrix4f;
import pl.nikowis.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/pl/nikowis/shaders/guis/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "/pl/nikowis/shaders//guis/guiFragmentShader.txt";

    private int location_transformationMatrix;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }


}
