package pl.nikowis.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.config.Config;
import pl.nikowis.models.FullModel;
import pl.nikowis.renderEngine.DisplayManager;

import java.util.List;

/**
 * Created by Nikodem on 12/27/2016.
 */
public class MovingEntity extends Entity {

    private float lightYDist;

    protected float move_speed = 200;
    protected float turn_speed = 200;
    protected float jump_power = 100;
    protected static final float GRAVITY = -100;
    protected static final float TERRAIN_HEIGHT = 0;

    protected float currentSpeed = 0;
    protected float currentTurnSpeed = 0;
    protected float upwardsSpeed = 0;

    protected boolean isInAir = false;

    public MovingEntity(FullModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public MovingEntity(FullModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Light> lights, float lightYDist) {
        super(model, position, rotX, rotY, rotZ, scale, lights);
        this.lightYDist = lightYDist;
    }

    public void move() {
        checkInputs();
        performMove();
        checkTerrainBounds();
    }

    private void performMove() {
        float adjustedTurnSpeed = currentTurnSpeed * DisplayManager.getFrameTimeSeconds();

        super.increaseRotation(0, adjustedTurnSpeed, 0);

        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        if (this.lights != null) {
            for (Light l : lights) {
                l.getPosition().y = position.getY() + lightYDist;
            }
            moveCarLights();
        }
        if (super.getPosition().y < TERRAIN_HEIGHT) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = TERRAIN_HEIGHT;
            if (this.lights != null) {
                for (Light l : lights) {
                    l.getPosition().y = TERRAIN_HEIGHT + lightYDist;
                }
            }
        }
    }

    private void moveCarLights() {
        if (lights.size() == 2) {
            float alfaRad = (float) Math.toRadians(rotY);
            float dx1 = (float) (flatPositionToLightDistance * Math.sin(alfaRad + betaRad));
            float dz1 = (float) (flatPositionToLightDistance * Math.cos(alfaRad + betaRad));
            float dx2 = (float) (flatPositionToLightDistance * Math.sin(alfaRad - betaRad));
            float dz2 = (float) (flatPositionToLightDistance * Math.cos(alfaRad - betaRad));
            Light l1 = lights.get(0);
            Light l2 = lights.get(1);
            l1.getPosition().x = position.x + dx1;
            l1.getPosition().z = position.z + dz1;
            l2.getPosition().x = position.x + dx2;
            l2.getPosition().z = position.z + dz2;
        }
    }

    private void jump() {
        isInAir = true;
        this.upwardsSpeed = jump_power;
    }

    protected void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            currentSpeed = +move_speed;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            currentSpeed = -move_speed;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            this.currentTurnSpeed = -turn_speed;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            this.currentTurnSpeed = turn_speed;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && (Config.ALLOW_MULTI_JUMP || !isInAir)) {
            jump();
        }
    }

    protected void checkTerrainBounds() {
        if (this.getPosition().x < 0) {
            this.getPosition().x = 0;
        }
        if (this.getPosition().z < 0) {
            this.getPosition().z = 0;
        }
        if (this.getPosition().x > Config.TERRAIN_SIZE) {
            this.getPosition().x = Config.TERRAIN_SIZE;
        }
        if (this.getPosition().z > Config.TERRAIN_SIZE) {
            this.getPosition().z = Config.TERRAIN_SIZE;
        }
    }

}
