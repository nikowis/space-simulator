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

    private float distanceFromEntity = 50;
    private float angleAroundEntity = 0;

    private float MOVE_SPEED =  200;
    private float SIDE_SPEED = 200;
    private float TURN_SPEED = 200;
    private float UP_DOWN_SPEED = 200;

    private float currentSpeed = 0;
    private float currentSideSpeed = 0;
    private float currentTurnSpeed = 0;
    private float currentUpDownSpeed = 0;

    private Vector3f followedPoint;
    private float rotX, rotY, rotZ;

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


    private void increasePosition(float dx, float dy, float dz) {
        this.followedPoint.x += dx;
        this.followedPoint.y += dy;
        this.followedPoint.z += dz;
    }

    private void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

   
    private void performMove() {
        float adjustedUpDownSpeed = currentUpDownSpeed * DisplayManager.getFrameTimeSeconds();
        this.increasePosition(0, adjustedUpDownSpeed, 0);

        float adjustedTurnSpeed = currentTurnSpeed * DisplayManager.getFrameTimeSeconds();
        this.increaseRotation(0, adjustedTurnSpeed, 0);

        float WSdistance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (WSdistance * Math.sin(Math.toRadians(rotY)));
        float dz = (float) (WSdistance * Math.cos(Math.toRadians(rotY)));
        float ADdistance = currentSideSpeed * DisplayManager.getFrameTimeSeconds();
        float dx2 = (float) (ADdistance * Math.sin(Math.toRadians(rotY + 90)));
        float dz2 = (float) (ADdistance * Math.cos(Math.toRadians(rotY + 90)));

        this.increasePosition(dx + dx2, 0, dz + dz2);
    }

    private void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            currentSpeed = +MOVE_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            currentSpeed = -MOVE_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentSideSpeed = -SIDE_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentSideSpeed = SIDE_SPEED;
        } else {
            this.currentSideSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            this.currentUpDownSpeed = -UP_DOWN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            this.currentUpDownSpeed = UP_DOWN_SPEED;
        } else {
            this.currentUpDownSpeed = 0;
        }
    }
}
