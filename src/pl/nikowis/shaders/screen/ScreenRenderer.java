package pl.nikowis.shaders.screen;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.entities.Camera;
import pl.nikowis.models.RawModel;
import pl.nikowis.renderEngine.Loader;
import pl.nikowis.toolbox.Maths;

public class ScreenRenderer {

	private RawModel quad;
	private ScreenShader shader;
	private ScreenFrameBuffers fbos;

	public ScreenRenderer(Loader loader, ScreenShader shader, Matrix4f projectionMatrix, ScreenFrameBuffers fbos) {
		this.shader = shader;
		this.fbos = fbos;
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		setUpVAO(loader);
	}

	public void render(List<ScreenTile> screens, Camera camera) {
		prepareRender(camera);	
		for (ScreenTile screen : screens) {
			Matrix4f modelMatrix = Maths.createTransformationMatrix(
					new Vector3f(screen.getX(), screen.getY(), screen.getZ()), screen.getRotX(), screen.getRotY(), screen.getRotZ(),
					screen.getSize());
			shader.loadModelMatrix(modelMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());
		}
		unbind();
	}
	
	private void prepareRender(Camera camera){
		shader.start();
		shader.loadViewMatrix(camera);
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
	}
	
	private void unbind(){
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	private void setUpVAO(Loader loader) {
		// Just x and z vectex positions here, y is set to 0 in v.shader
		float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		quad = loader.loadToVAO(vertices, 2);
	}

}
