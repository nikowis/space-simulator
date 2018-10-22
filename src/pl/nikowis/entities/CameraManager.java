package pl.nikowis.entities;

import org.lwjgl.input.Keyboard;

/**
 * Manager of different cameras.
 * Created by Nikodem on 12/31/2016.
 */
public class CameraManager {
    private Camera currentCamera;

    private ThirdPersonCamera thirdPersonCamera;
    private StaticCamera staticCamera;
    private TurningCamera turningCamera;

    /**
     * Constructor.
     *
     * @param staticCamera      staticCamera
     * @param thirdPersonCamera thirdPersonCamera
     * @param turningCamera     turningCamera
     */
    public CameraManager(StaticCamera staticCamera, ThirdPersonCamera thirdPersonCamera, TurningCamera turningCamera) {
        this.staticCamera = staticCamera;
        this.thirdPersonCamera = thirdPersonCamera;
        this.turningCamera = turningCamera;
        currentCamera = thirdPersonCamera;
    }

    public void moveCurrentCamera() {
        currentCamera.move();
    }

    /**
     * Changes the current camera on input.
     */
    public void checkInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            currentCamera = staticCamera;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            currentCamera = thirdPersonCamera;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_3) && turningCamera != null) {
            currentCamera = turningCamera;
        }
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }
}
