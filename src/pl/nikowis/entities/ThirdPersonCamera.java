package pl.nikowis.entities;

import org.lwjgl.input.Mouse;

/**
 * Camera that follows the given entity.
 * Created by Nikodem on 12/25/2016.
 */
public class ThirdPersonCamera extends Camera {

    private float distanceFromEntity = 50;
    private float angleAroundEntity = 0;

    private MovingEntity movingEntity;

    public ThirdPersonCamera(MovingEntity movingEntity) {
        pitch = 20;
        this.movingEntity = movingEntity;
    }

    public void move() {
        calculateZoom();
        calculatePitch();
        calculateAngleAroundEntity();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = movingEntity.getRotY() + angleAroundEntity;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = movingEntity.getPosition().x - offsetX;
        position.z = movingEntity.getPosition().z - offsetZ;
        position.y = movingEntity.getPosition().y + verticalDistance;
        this.yaw = 180 - (movingEntity.getRotY() + angleAroundEntity);
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromEntity * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromEntity * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromEntity -= zoomLevel;
    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundEntity() {
        if (Mouse.isButtonDown(0)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundEntity -= angleChange;
        }
    }
}
