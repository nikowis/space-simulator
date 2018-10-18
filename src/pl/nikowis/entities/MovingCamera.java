package pl.nikowis.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * Camera that moves.
 * Created by Nikodem on 12/25/2016.
 */
public class MovingCamera extends ThirdPersonCamera {

    public MovingCamera(Vector3f followPosition, Vector3f initialRotation) {
        super(new MovingEntity(null, followPosition, initialRotation.x, initialRotation.y, initialRotation.z, 0));
        pitch = 20;
    }

    @Override
    public void move() {
        ((MovingEntity) entity).move();
        super.move();
    }
}
