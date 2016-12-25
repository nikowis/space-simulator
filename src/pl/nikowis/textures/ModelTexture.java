package pl.nikowis.textures;

/**
 * Created by Nikodem on 12/24/2016.
 */
public class ModelTexture {
    private int textureId;
    private float shineDamper = 1;
    private float reflectivity = 0;

    public ModelTexture(int id) {
        this.textureId = id;
    }

    public int getID() {
        return textureId;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    /**
     * Sets how wide should be the shining effect
     * when comparing the distance from the reflected light and the camera direction.
     * @param shineDamper value
     */
    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    /**
     * Sets how shiny should an object be.
     * @param reflectivity value
     */
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
