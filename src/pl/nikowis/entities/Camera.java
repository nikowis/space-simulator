package pl.nikowis.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * Abstract camera class.
 * Created by Nikodem on 12/31/2016.
 */
public abstract class Camera {

    protected Vector3f position = new Vector3f(0, 0, 0);
    protected float pitch;
    protected float yaw;
    protected float roll;

    /**
     * Position of the camera.
     *
     * @return position
     */
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Controls up/down turns.
     * How 'high' the camera is.
     *
     * @return pitch
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Controls up/down turns.
     * How 'high' the camera is.
     *
     * @param pitch pitch
     */
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    /**
     * Controls left/right turns.
     *
     * @return yaw
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * Controls left/right turns.
     *
     * @param yaw yaw
     */
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    /**
     * Controls tilt level of the camera.
     *
     * @return roll
     */
    public float getRoll() {
        return roll;
    }

    /**
     * Controls tilt level of the camera.
     *
     * @param roll roll
     */
    public void setRoll(float roll) {
        this.roll = roll;
    }

    public abstract void move();
}
