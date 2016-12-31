package pl.nikowis.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * Static non-moving camera.
 * Created by Nikodem on 12/31/2016.
 */
public class StaticCamera extends Camera {

    public StaticCamera(Vector3f position, float pitch) {
        this.position = position;
        this.pitch = pitch;
    }

    @Override
    public void move() {
        //nothing
    }
}
