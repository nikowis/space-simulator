package pl.nikowis.entities;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.models.FullModel;

import java.util.List;

/**
 * Created by Nikodem on 1/15/2017.
 */
public class CarEntity extends MovingEntity {

    private float lightYDist;

    public CarEntity(FullModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public CarEntity(FullModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, List<Light> lights, float lightYDist) {
        super(model, position, rotX, rotY, rotZ, scale, lights);
        this.lightYDist = lightYDist;
    }

    @Override
    protected void performMove() {
        super.performMove();
        if (this.lights != null) {
            for (Light l : lights) {
                l.getPosition().y = position.getY() + lightYDist;
            }
            moveCarLights();
        }
        if (super.getPosition().y < TERRAIN_HEIGHT) {
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

}
