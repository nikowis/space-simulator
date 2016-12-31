package pl.nikowis.entities;

import org.lwjgl.util.vector.Vector3f;

/**
 * Camera that turns towards the moving entity.
 * Created by Nikodem on 12/31/2016.
 */
public class TurningCamera extends Camera {

    private MovingEntity movingEntity;

    public TurningCamera(Vector3f position, float pitch, MovingEntity movingEntity) {
        this.movingEntity = movingEntity;
        this.position = position;
        this.pitch = pitch;
    }

    @Override
    public void move() {
        moveYaw();
        movePitch();
    }

    private void movePitch() {
        float dx = movingEntity.getPosition().x - this.position.x;
        float dz = movingEntity.getPosition().z - this.position.z;
        float verticalDistance = this.getPosition().getY() - movingEntity.getPosition().getY();
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        double radianAngle = Math.atan(verticalDistance / horizontalDistance);
        float degreeAngle = (float) ((radianAngle / Math.PI) * 180);
        pitch = degreeAngle;
    }

    private void moveYaw() {
        float horizontalDist = movingEntity.getPosition().x - this.position.x;
        float verticalDist = movingEntity.getPosition().z - this.position.z;
        double radianAngle = Math.atan(horizontalDist / verticalDist);
        float degreeAngle = (float) ((radianAngle / Math.PI) * 180);
        this.yaw = 180 - degreeAngle;
    }

}
