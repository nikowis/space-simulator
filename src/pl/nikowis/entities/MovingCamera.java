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

    private float MOVE_SPEED = 200;
    private float SIDE_SPEED = 200;
    private float TURN_SPEED = 200;
    private float UP_DOWN_SPEED = 200;
    private float PITCH_SPEED = 50;

    private float currentSpeed = 0;
    private float currentSideSpeed = 0;
    private float currentTurnSpeed = 0;
    private float currentUpDownSpeed = 0;
    private float currentPitchSpeed = 0;

    private Vector3f followedPoint;
    private float rotX, rotY, rotZ;

    public MovingCamera(Vector3f position) {
        this.position = position;
        this.followedPoint = new Vector3f(position.x + 20, position.y, position.z + 20);
        pitch = 20;
    }

    public void move() {
        checkInputs();
        movePosition();

        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromEntity -= zoomLevel;

        float adjustedTurnSpeed = currentTurnSpeed * DisplayManager.getFrameTimeSeconds();
        this.increaseRotation(0, adjustedTurnSpeed, 0);

        float adjustedPitchSpeed = currentPitchSpeed * DisplayManager.getFrameTimeSeconds();
        pitch += adjustedPitchSpeed;
        rotZ++;
        float horizontalDistance = (float) (distanceFromEntity * Math.cos(Math.toRadians(pitch)));
        float verticalDistance = (float) (distanceFromEntity * Math.sin(Math.toRadians(pitch)));
        calculateCameraPosition(horizontalDistance, verticalDistance);
    }


    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(rotY)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(rotY)));
        position.x = followedPoint.x - offsetX;
        position.z = followedPoint.z - offsetZ;
        position.y = followedPoint.y + verticalDistance;
        this.yaw = 180 - rotY;
    }

    private void movePosition() {
        float dy = currentUpDownSpeed * DisplayManager.getFrameTimeSeconds();
        float dy2 =  (currentSpeed * DisplayManager.getFrameTimeSeconds()) * (float)Math.sin(Math.toRadians(pitch + 180));
        float WSdistance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (WSdistance * Math.sin(Math.toRadians(rotY)));
        float dz = (float) (WSdistance * Math.cos(Math.toRadians(rotY)));
        float ADdistance = currentSideSpeed * DisplayManager.getFrameTimeSeconds();
        float dx2 = (float) (ADdistance * Math.sin(Math.toRadians(rotY + 90)));
        float dz2 = (float) (ADdistance * Math.cos(Math.toRadians(rotY + 90)));

        this.increasePosition(dx + dx2, dy + dy2, dz + dz2);
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

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            this.currentPitchSpeed = -PITCH_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            this.currentPitchSpeed = PITCH_SPEED;
        } else {
            this.currentPitchSpeed = 0;
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
