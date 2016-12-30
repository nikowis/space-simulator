package pl.nikowis.shaders;

/**
 * Implementation of {@link ShaderProgram}.
 * Used to manipulate uniform variables.
 * Shader for terrain only.
 * Created by Nikodem on 12/26/2016.
 */
public class TerrainShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src\\pl\\nikowis\\shaders\\terrainVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src\\pl\\nikowis\\shaders\\terrainFragmentShader.glsl";

    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;


    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        super.getAllUniformLocations();

        location_backgroundTexture = super.getUniformLocation("backgroundTexture");
        location_rTexture = super.getUniformLocation("rTexture");
        location_gTexture = super.getUniformLocation("gTexture");
        location_bTexture = super.getUniformLocation("bTexture");
        location_blendMap = super.getUniformLocation("blendMap");

    }

    public void connectTextureUnits() {
        super.loadInt(location_backgroundTexture, 0);
        super.loadInt(location_rTexture, 1);
        super.loadInt(location_gTexture, 2);
        super.loadInt(location_bTexture, 3);
        super.loadInt(location_blendMap, 4);
    }


    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }
}
