package pl.nikowis.renderEngine;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import pl.nikowis.models.RawModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader for obj files.
 * Created by Nikodem on 12/25/2016.
 */
public class OBJLoader {

    public static final String VERTEX_PREFIX = "v ";
    public static final String SPACE_SEPARATOR = " ";
    public static final String TEXTURE_PREFIX = "vt ";
    public static final String NORMAL_PREFIX = "vn ";
    public static final String SLASH_SEPARATOR = "/";
    public static final String FACE_PREFIX = "f ";
    public static final String RESOURCES_PATH = "res/";
    private static final String OBJ_EXTENSION = ".obj";

    /**
     * Reads the obj file and extracts all the data into a {@link RawModel}.
     * @param fileName name of the obj file ( without the extension ).
     * @param loader loader class instance
     * @return {@link RawModel} model
     */
    public static RawModel loadObjModel(String fileName, Loader loader) {
        FileReader fr;
        try {
            fr = new FileReader(new File(RESOURCES_PATH + fileName + OBJ_EXTENSION));
        } catch (FileNotFoundException e) {
            System.err.println("Couln't load file!");
            e.printStackTrace();
            return null;
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray;

        try {
            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(SPACE_SEPARATOR);
                if (line.startsWith(VERTEX_PREFIX)) {
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(currentLine[1])
                            , Float.parseFloat(currentLine[2])
                            , Float.parseFloat(currentLine[3])
                    );
                    vertices.add(vertex);
                } else if (line.startsWith(TEXTURE_PREFIX)) {
                    Vector2f texture = new Vector2f(
                            Float.parseFloat(currentLine[1])
                            , Float.parseFloat(currentLine[2])
                    );
                    textures.add(texture);
                } else if (line.startsWith(NORMAL_PREFIX)) {
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(currentLine[1])
                            , Float.parseFloat(currentLine[2])
                            , Float.parseFloat(currentLine[3])
                    );
                    normals.add(normal);
                } else if (line.startsWith(FACE_PREFIX)) {
                    textureArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }
            while (line != null) {
                if (!line.startsWith(FACE_PREFIX)) {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(SPACE_SEPARATOR);
                String[] vertex1 = currentLine[1].split(SLASH_SEPARATOR);
                String[] vertex2 = currentLine[2].split(SLASH_SEPARATOR);
                String[] vertex3 = currentLine[3].split(SLASH_SEPARATOR);
                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];
        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
    }

    private static void processVertex(String[] vertexData, List<Integer> indices
            , List<Vector2f> textures, List<Vector3f> normals
            , float[] textureArray, float[] normalsArray) {

        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        if(!vertexData[1].isEmpty()) {
            Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
            textureArray[currentVertexPointer * 2] = currentTex.x;
            textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
        }
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;

    }
}
