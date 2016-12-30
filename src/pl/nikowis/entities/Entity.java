package pl.nikowis.entities;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.models.FullModel;

/**
 * Created by Nikodem on 12/24/2016.
 */
public class Entity {

    protected FullModel model;
    protected Vector3f position;
    protected float rotX, rotY, rotZ;
    protected float scale;
    protected Light light;

    public Entity(FullModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        light = null;
    }

    public Entity(FullModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Light light, float lightYDiff) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.light = light;
        light.getPosition().x = position.getX();
        light.getPosition().y = position.getY() + lightYDiff;
        light.getPosition().z = position.getZ();
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

    public Light getLight() {
        return light;
    }
}
