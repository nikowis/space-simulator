package pl.nikowis.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.renderEngine.DisplayManager;

/**
 * Camera that moves.
 * Created by Nikodem on 12/25/2016.
 */
public class MovingCamera extends Camera {

    protected float distanceFromEntity = 50;
    protected float angleAroundEntity = 0;

    protected float move_speed = 200;
    protected float side_speed = 200;
    protected float currentSpeed = 0;
    protected float currentSideSpeed = 0;

    protected Vector3f followedPoint;
    protected float rotX, rotY, rotZ;

    public MovingCamera(Vector3f position) {
        this.position = position;
        this.followedPoint = new Vector3f(position.x + 20, position.y, position.z + 20);
        pitch = 20;
    }

    public void move() {
        checkInputs();
        performMove();
        calculateZoom();
        calculatePitch();
        calculateAngleAroundEntity();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
    }


    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = rotY + angleAroundEntity;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = followedPoint.x - offsetX;
        position.z = followedPoint.z - offsetZ;
        position.y = followedPoint.y + verticalDistance;
        this.yaw = 180 - (rotY + angleAroundEntity);
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


    public void increasePosition(float dx, float dy, float dz) {
        this.followedPoint.x += dx;
        this.followedPoint.y += dy;
        this.followedPoint.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }


    protected void performMove() {
//        float adjustedTurnSpeed = currentSideSpeed * DisplayManager.getFrameTimeSeconds();
//
//        this.increaseRotation(0, adjustedTurnSpeed, 0);

        float WSdistance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (WSdistance * Math.sin(Math.toRadians(rotY)));
        float dz = (float) (WSdistance * Math.cos(Math.toRadians(rotZ)));
        float ADdistance = currentSideSpeed * DisplayManager.getFrameTimeSeconds();
        float dx2 = (float) (ADdistance * Math.cos(Math.toRadians(rotY)));
        float dz2 = (float) (ADdistance * Math.sin(Math.toRadians(rotZ)));

        this.increasePosition(dx + dx2, 0, dz + dz2);
    }

    protected void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            currentSpeed = +move_speed;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            currentSpeed = -move_speed;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentSideSpeed = -side_speed;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentSideSpeed = side_speed;
        } else {
            this.currentSideSpeed = 0;
        }

    }
}
