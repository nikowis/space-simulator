package pl.nikowis.entities;

import org.lwjgl.input.Keyboard;

/**
 * Manager of different cameras.
 * Created by Nikodem on 12/31/2016.
 */
public class CameraManager {
    private Camera currentCamera;

    private Camera firstCamera;
    private Camera secondCamera;

    public CameraManager(Camera firstCamera, Camera secondCamera) {
        this.firstCamera = firstCamera;
        this.secondCamera = secondCamera;
        currentCamera = firstCamera;
    }

    public void moveCurrentCamera() {
        currentCamera.move();
    }

    /**
     * Changes the current camera on input.
     */
    public void checkInput() {
        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
            currentCamera = firstCamera;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
            currentCamera = secondCamera;
        }
    }

    public Camera getCurrentCamera() {
        return currentCamera;
    }
}
