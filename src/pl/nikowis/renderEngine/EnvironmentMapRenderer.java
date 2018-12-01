package pl.nikowis.renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.CubeMapCamera;
import pl.nikowis.textures.EnvironmentMapTexture;

/**
 * Created by Nikodem on 12/1/2018.
 */
public class EnvironmentMapRenderer {

    public static void renderEnvironmentMap(EnvironmentMapTexture cubeMap, Vector3f center, MasterRenderer masterRenderer) {

        CubeMapCamera camera = new CubeMapCamera(center);

        int fbo = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

        int depthBuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, cubeMap.size, cubeMap.size);

        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBuffer);
        GL11.glViewport(0, 0, cubeMap.size, cubeMap.size);

        for (int i = 0; i < 6; i++) {
            GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, cubeMap.textureId, 0);
            camera.switchToFace(i);

            masterRenderer.renderEnvironmentMap(camera);
        }

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

        GL11.glViewport(0,0, Display.getWidth(), Display.getHeight());

        GL30.glDeleteRenderbuffers(depthBuffer);
        GL30.glDeleteFramebuffers(fbo);

    }
}
