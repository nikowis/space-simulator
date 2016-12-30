package pl.nikowis.shaders.naked;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.shaders.ShaderProgram;

/**
 * Shader for entities without textures for presenting different shading and reflection models.
 * Created by Nikodem on 12/30/2016.
 */
public abstract class NakedShader extends ShaderProgram {

    protected int location_baseColour;
    protected int location_phongShadingEnabled;
    protected int location_gouraudShadingEnabled;
    protected int location_phongReflectionEnabled;
    protected int location_blinnReflectionEnabled;


    public NakedShader(String vertexFile, String fragmentFile, String geometryFile) {
        super(vertexFile, fragmentFile, geometryFile);
    }

    public NakedShader(String vertexFile, String fragmentFile) {
        super(vertexFile, fragmentFile);
    }

    public void loadBaseColour(Vector3f baseColour) {
        loadVector(location_baseColour, baseColour);
    }

    public void loadPhongGouardShading(boolean phongShadingEnabled, boolean gouraudShadingEnabled) {
        if (phongShadingEnabled == gouraudShadingEnabled) {
            throw new IllegalStateException("Cannot enable/disable both phong and Gouraud shading models.");
        }
        loadBoolean(location_phongShadingEnabled, phongShadingEnabled);
        loadBoolean(location_gouraudShadingEnabled, gouraudShadingEnabled);
    }

    public void loadPhongBlinnReflection(boolean phongReflectionEnabled, boolean blinnReflectionEnabled) {
        if (phongReflectionEnabled == true && phongReflectionEnabled == blinnReflectionEnabled) {
            throw new IllegalStateException("Cannot enable both Phong and Blinn reflection models.");
        }
        loadBoolean(location_phongReflectionEnabled, phongReflectionEnabled);
        loadBoolean(location_blinnReflectionEnabled, blinnReflectionEnabled);
    }

    @Override
    protected void getAllUniformLocations() {
        super.getAllUniformLocations();
        location_baseColour = getUniformLocation("baseColour");
        location_phongShadingEnabled = getUniformLocation("phongShadingEnabled");
        location_gouraudShadingEnabled = getUniformLocation("gouraudShadingEnabled");
        location_phongReflectionEnabled = getUniformLocation("phongReflectionEnabled");
        location_blinnReflectionEnabled = getUniformLocation("blinnReflectionEnabled");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }
}
