package pl.nikowis.shaders.particles;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Nikodem on 12/1/2018.
 */
public class AsteroidsBeltGenerator {

    private ParticleTexture texture;

    public AsteroidsBeltGenerator(ParticleTexture texture) {
        this.texture = texture;
    }

    public void generateAsteroids(Vector3f center, int count) {
        for (int i = 0; i < count; i++) {
            float dirX = (float) Math.random() * 200f;
            float dirZ = (float) Math.random() * 200f;
            float dirY = (float) Math.random() * 100f;
            Vector3f position = new Vector3f(center.x + dirX, center.y + dirY, center.z + dirZ);
            new Particle(texture, position, new Vector3f(0, 0, 0), 0, Float.MAX_VALUE, 0, 10);
        }
    }


}
