package pl.nikowis.entities;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.models.FullModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikodem on 12/24/2016.
 */
public class Entity {

    protected FullModel model;
    protected Vector3f position;
    protected float rotX, rotY, rotZ;
    protected float scale;
    protected List<Light> lights;

    protected float flatPositionToLightDistance;
    protected float betaRad;

    private int textureIndex = 0;

    public Entity(FullModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public Entity(FullModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textureIndex = textureIndex;
    }

    public Entity(FullModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Light> lights) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.lights = lights;
        for (Light light : lights) {
            Vector3f relativeLightPos = light.getPosition();
            flatPositionToLightDistance = (float) Math.sqrt(relativeLightPos.x * relativeLightPos.x + relativeLightPos.z * relativeLightPos.z);
            betaRad = (float) Math.atan(relativeLightPos.x / relativeLightPos.z);
            Vector3f newLightPos = new Vector3f(position.x + relativeLightPos.x, position.y + relativeLightPos.y, position.z + relativeLightPos.z);
            light.setPosition(newLightPos);
        }
    }

    public Entity(FullModel model, Vector3f position, int rotX, int rotY, int rotZ, int scale, Light light) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.lights = new ArrayList<>();
        this.lights.add(light);
        Vector3f relativeLightPos = light.getPosition();
        Vector3f newLightPos = new Vector3f(position.x + relativeLightPos.x, position.y + relativeLightPos.y, position.z + relativeLightPos.z);
        light.setPosition(newLightPos);
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public float getTextureXOffset() {
        int column = textureIndex%model.getTexture().getNumberOfRows();
        return (float) column/(float)model.getTexture().getNumberOfRows();
    }

    public float getTextureYOffset() {
        int row = textureIndex/model.getTexture().getNumberOfRows();
        return (float) row/(float)model.getTexture().getNumberOfRows();
    }

    public FullModel getModel() {
        return model;
    }

    public void setModel(FullModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }
}
