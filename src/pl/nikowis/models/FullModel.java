package pl.nikowis.models;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.textures.ModelTexture;

/**
 * Model containing texture, and untextured informations.
 * Created by Nikodem on 12/24/2016.
 */
public class FullModel {

    private RawModel rawModel;
    private ModelTexture texture;
    private float shineDamper = 100;
    private float reflectivity = 1;
    private Vector3f defaultColour;
    private float cubeMapReflection;

    /**
     * Constructor.
     * @param rawModel raw model with all vertices info
     * @param texture texture to use
     * @param defaultColour default colour to use without texture
     */
    public FullModel(RawModel rawModel, ModelTexture texture, Vector3f defaultColour) {
        this.rawModel = rawModel;
        this.texture = texture;
        this.defaultColour = defaultColour;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public Vector3f getDefaultColour() {
        return defaultColour;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public float getCubeMapReflection() {
        return cubeMapReflection;
    }

    public void setCubeMapReflection(float cubeMapReflection) {
        this.cubeMapReflection = cubeMapReflection;
    }
}
