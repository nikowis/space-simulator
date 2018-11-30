package pl.nikowis.shaders.particles;

import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.renderEngine.DisplayManager;

/**
 * Created by Nikodem on 12/1/2018.
 */
public class ParticleGenerator {
    private float particlesPerSecond;
    private float speed;
    private float gravityFactor;
    private float lifeTime;

    public ParticleGenerator(float particlesPerSecond, float speed, float gravityFactor, float lifeTime) {
        this.particlesPerSecond = particlesPerSecond;
        this.speed = speed;
        this.gravityFactor = gravityFactor;
        this.lifeTime = lifeTime;
    }

    public void generateParticles(Vector3f systemCenter){
        float delta = DisplayManager.getFrameTimeSeconds();
        float particlesToCreate = particlesPerSecond * delta;
        int count = (int) Math.floor(particlesToCreate);
        float partialParticle = particlesToCreate % 1;
        for(int i=0;i<count;i++){
            emitParticle(systemCenter);
        }
        if(Math.random() < partialParticle){
            emitParticle(systemCenter);
        }
    }

    private void emitParticle(Vector3f center){
        float dirX = (float) Math.random() * 2f - 1f;
        float dirZ = (float) Math.random() * 2f - 1f;
        Vector3f velocity = new Vector3f(dirX, 1, dirZ);
        velocity.normalise();
        velocity.scale(speed);
        new Particle(new Vector3f(center), velocity, gravityFactor, lifeTime, 0, 1);
    }

}
