package pl.nikowis.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Nikodem on 12/25/2016.
 */
public class Camera {

    private static final float MOVE_STEP = 0.05f;

    private Vector3f position = new Vector3f(0, 15, 0);
    //how high the camera is
    private float pitch=10;
    //how right or left the camera is
    private float yaw;
    //how much the camera is tilted
    private float roll;

    public Camera() {
    }

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += MOVE_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= MOVE_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.y += MOVE_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.y -= MOVE_STEP;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            position.z += MOVE_STEP * 2;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            position.z -= MOVE_STEP * 2;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
