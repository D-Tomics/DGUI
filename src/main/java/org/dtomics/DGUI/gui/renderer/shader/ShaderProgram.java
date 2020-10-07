package org.dtomics.DGUI.gui.renderer.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class helps to manage a shader file.
 *
 * @author Abdul Kareem
 */
public abstract class ShaderProgram {

    private static final int MAX_SHADERS_PER_PROGRAM = 2;
    private static final String LOCATION = "/";
    private static final float[] mat4x4 = new float[16];
    private final String location;
    private final int program;
    private final Map<String, Integer> uniforms;

    protected ShaderProgram(String shader) {
        this(LOCATION, shader);
    }

    protected ShaderProgram(String location, String shader) {
        this.location = location;
        program = createAttachLinkAndValidateProgram(loadAndCompile(shader));
        uniforms = new HashMap<>();
    }

    public void start() {
        GL20.glUseProgram(program);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    protected void loadLocations(String... names) {
        for (String name : names)
            uniforms.put(name, GL20.glGetUniformLocation(program, name));
    }

    protected void loadLocation(String name) {
        uniforms.put(name, GL20.glGetUniformLocation(program, name));
    }

    private int getLocation(String name) {
        if (!uniforms.containsKey(name))
            loadLocation(name);
        return uniforms.get(name);
    }

    public void cleanUp() {
        GL20.glDeleteProgram(program);
    }

    protected void loadMat4(String name, Matrix4f mat) {
        GL20.glUniformMatrix4fv(getLocation(name), false, mat.get(mat4x4));
    }

    protected void loadVec4f(String name, Vector4f vec) {
        loadVec4f(name, vec.x, vec.y, vec.z, vec.w);
    }

    protected void loadVec3f(String name, Vector3f vec) {
        loadVec3f(name, vec.x, vec.y, vec.z);
    }

    protected void loadVec2f(String name, Vector2f vec) {
        loadVec2f(name, vec.x, vec.y);
    }

    protected void loadVec4f(String name, float x, float y, float z, float w) {
        GL20.glUniform4f(getLocation(name), x, y, z, w);
    }

    protected void loadVec3f(String name, float x, float y, float z) {
        GL20.glUniform3f(getLocation(name), x, y, z);
    }

    protected void loadVec2f(String name, float x, float y) {
        GL20.glUniform2f(getLocation(name), x, y);
    }

    protected void loadFloat(String name, float val) {
        GL20.glUniform1f(getLocation(name), val);
    }

    protected void loadInt(String name, int val) {
        GL20.glUniform1i(getLocation(name), val);
    }

    private int createAttachLinkAndValidateProgram(int[] shaders) {
        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, shaders[0]);
        GL20.glAttachShader(program, shaders[1]);
        GL20.glDeleteShader(shaders[0]);
        GL20.glDeleteShader(shaders[1]);
        GL20.glLinkProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) != GL11.GL_TRUE)
            System.err.println(GL20.glGetProgramInfoLog(program));
        GL20.glValidateProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) != GL11.GL_TRUE)
            System.err.println(GL20.glGetProgramInfoLog(program));
        return program;
    }

    private int[] loadAndCompile(String shader) {
        StringBuilder[] source = new StringBuilder[MAX_SHADERS_PER_PROGRAM];

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(location + shader)))) {
            String line = "";

            int i = -1;
            while ((line = reader.readLine()) != null) {
                if (line.equals("#start")) {
                    i++;
                    source[i] = new StringBuilder();
                    continue;
                }
                source[i].append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println(shader + " ::ERROR_READING_FILE::");
        }

        return new int[]{
                compileShader(source[0].toString(), GL20.GL_VERTEX_SHADER),
                compileShader(source[1].toString(), GL20.GL_FRAGMENT_SHADER)
        };

    }

    private int compileShader(String shaderSource, int type) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, shaderSource);
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) != GL11.GL_TRUE)
            System.err.println(GL20.glGetShaderInfoLog(shader));
        return shader;
    }
}
