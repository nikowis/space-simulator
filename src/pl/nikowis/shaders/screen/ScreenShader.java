package pl.nikowis.shaders.screen;


import org.lwjgl.util.vector.Matrix4f;
import pl.nikowis.entities.Camera;
import pl.nikowis.shaders.ShaderProgram;
import pl.nikowis.toolbox.Maths;

public class ScreenShader extends ShaderProgram {

	private final static String VERTEX_FILE = "/pl/nikowis/shaders/screen/screenVertex.txt";
	private final static String FRAGMENT_FILE = "/pl/nikowis/shaders/screen/screenFragment.txt";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;

	public ScreenShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}

}
