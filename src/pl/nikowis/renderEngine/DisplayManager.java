package pl.nikowis.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import pl.nikowis.config.Config;

/**
 * Created by Nikodem on 12/22/2016.
 */
public class DisplayManager {

    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay() {
        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle(Config.WINDOW_TITLE);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplay() {
        Display.sync(Config.FPS_CAP);
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;

    }

    /**
     * Calculates the time for last frame (useful for smooth time dependent object movement).
     *
     * @return time in seconds
     */
    public static float getFrameTimeSeconds() {
        return delta;
    }


    public static void closeDisplay() {
        Display.destroy();
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
}
