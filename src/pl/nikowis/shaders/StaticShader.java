package pl.nikowis.shaders;

import org.lwjgl.util.vector.Matrix4f;
import pl.nikowis.entities.Camera;
import pl.nikowis.toolbox.Maths;

/**
 * Created by Nikodem on 12/24/2016.
 */
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src\\pl\\nikowis\\shaders\\vertexShader.glsl";
    private static final String FRAGMENT_FILE = "src\\pl\\nikowis\\shaders\\fragmentShader.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_projectionMatrix = super.getUniformLocation("viewMatrix");
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadTransformationMatrix(Matrix4f transformation) {
        super.loadMatrix(location_transformationMatrix, transformation);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }
}
