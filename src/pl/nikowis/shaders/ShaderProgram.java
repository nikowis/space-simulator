package pl.nikowis.shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.Camera;
import pl.nikowis.entities.Light;
import pl.nikowis.toolbox.Maths;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * Abstract class to manipulate uniform variables and load glsl shaders.
 * Created by Nikodem on 12/24/2016.
 */
public abstract class ShaderProgram {

    //can crash when too many
    public static final int MAX_LIGHTS = 10;
    private final boolean geometryShaderEnabled;

    private int programId;
    private int vertexShaderID;
    private int fragmentShaderID;
    private int geometryShaderID;

    protected int location_transformationMatrix;
    protected int location_projectionMatrix;
    protected int location_viewMatrix;
    protected int location_lightPosition[];
    protected int location_lightColour[];
    protected int location_attenuation[];
    protected int location_shineDamper;
    protected int location_reflectivity;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    /**
     * Constructor
     *
     * @param vertexFile   vertex shader in glsl
     * @param fragmentFile fragment shader in glsl
     */
    public ShaderProgram(String vertexFile, String fragmentFile, String geometryFile) {
        geometryShaderEnabled = true;
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        geometryShaderID = loadShader(geometryFile, GL32.GL_GEOMETRY_SHADER);
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShaderID);
        GL20.glAttachShader(programId, fragmentShaderID);
        GL20.glAttachShader(programId, geometryShaderID);
        bindAttributes();
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);
        getAllUniformLocations();
    }

    public ShaderProgram(String vertexFile, String fragmentFile) {
        geometryShaderEnabled = false;
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShaderID);
        GL20.glAttachShader(programId, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);
        getAllUniformLocations();
    }

    protected void getAllUniformLocations() {
        location_transformationMatrix = getUniformLocation("transformationMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_shineDamper = getUniformLocation("shineDamper");
        location_reflectivity = getUniformLocation("reflectivty");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition[i] = getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = getUniformLocation("attenuation[" + i + "]");
        }
    }

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programId, uniformName);
    }

    public void start() {
        GL20.glUseProgram(programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    /**
     * Cleans up the program, vertex shader and the fragment shader.
     */
    public void cleanUp() {
        stop();
        GL20.glDetachShader(programId, vertexShaderID);
        GL20.glDetachShader(programId, fragmentShaderID);
        if (geometryShaderEnabled) {
            GL20.glDetachShader(programId, geometryShaderID);
        }
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        if (geometryShaderEnabled) {
            GL20.glDeleteShader(geometryShaderID);
        }
        GL20.glDeleteProgram(programId);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programId, attribute, variableName);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadInt(int location, int value) {
        GL20.glUniform1i(location, value);
    }

    protected void loadVector(int location, Vector3f vector3f) {
        GL20.glUniform3f(location, vector3f.x, vector3f.y, vector3f.z);
    }

    protected void loadBoolean(int location, boolean value) {
        float toLoad = 0;
        if (value) {
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);
    }

    protected void loadMatrix(int location, Matrix4f matrix4f) {
        matrix4f.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file!");
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {

            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader. " + file);
            System.exit(-1);
        }
        return shaderID;
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        loadMatrix(location_projectionMatrix, projection);
    }

    public void loadTransformationMatrix(Matrix4f transformation) {
        loadMatrix(location_transformationMatrix, transformation);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadShineVariables(float damper, float reflectivity) {
        loadFloat(location_shineDamper, damper);
        loadFloat(location_reflectivity, reflectivity);
    }

    /**
     * Loads the lights to the memory.
     *
     * @param lights list of lights with position, colour and attenuation(optional).
     */
    public void loadLights(List<Light> lights) {
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < lights.size()) {
                loadVector(location_lightPosition[i], lights.get(i).getPosition());
                loadVector(location_lightColour[i], lights.get(i).getColour());
                loadVector(location_attenuation[i], lights.get(i).getAttenuation());
            } else {
                //filling black lights for the remaining slots
                loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
                loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
                loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
            }
        }
    }
}
