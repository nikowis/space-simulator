package pl.nikowis.entities;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Nikodem on 12/1/2018.
 */
public class CubeMapCamera extends Camera{
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 200f;
    private static final float FOV = 90;// don't change!
    private static final float ASPECT_RATIO = 1;

    private Matrix4f projectionMatrix = new Matrix4f();
    private Matrix4f viewMatrix = new Matrix4f();
    private Matrix4f projectionViewMatrix = new Matrix4f();

    public CubeMapCamera(Vector3f center) {
        this.position = center;
        createProjectionMatrix();
    }

    public void switchToFace(int faceIndex) {
        switch (faceIndex) {
            case 0:
                pitch = 0;
                yaw = 90;
                break;
            case 1:
                pitch = 0;
                yaw = -90;
                break;
            case 2:
                pitch = -90;
                yaw = 180;
                break;
            case 3:
                pitch = 90;
                yaw = 180;
                break;
            case 4:
                pitch = 0;
                yaw = 180;
                break;
            case 5:
                pitch = 0;
                yaw = 0;
                break;
        }
        updateViewMatrix();
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    @Override
    public void move() {

    }


    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }


    public void reflect(float height) {

    }


    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }


    public Matrix4f getProjectionViewMatrix() {
        return projectionViewMatrix;
    }

    private void createProjectionMatrix() {
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
        float x_scale = y_scale / ASPECT_RATIO;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    private void updateViewMatrix() {
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(180), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);

        Matrix4f.mul(projectionMatrix, viewMatrix, projectionViewMatrix);
    }
}
