package com.gaius.opengl.renderer;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.gaius.opengl.utils.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SimpleRenderer implements GLSurfaceView.Renderer {

    private String vertexShader = "#version 300 es \n" //这行是着色器的版本，OpenGL ES 2.0版本可以不写
            + "layout (location = 0) in vec4 vPosition;\n"
            + "void main() { \n"
            + "gl_Position = vPosition;\n"
            + "gl_PointSize = 10.0;\n"
            + "}\n";

    private String fragmentShader = "#version 300 es \n"
            + "precision mediump float;\n"
            + "out vec4 fragColor;\n"
            + "void main() { \n"
            + "fragColor = vec4(1.0,1.0,1.0,1.0);\n"
            + "}\n";

    /*private float[] vertexPoints = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };*/
    /*private float[] vertexPoints = new float[]{
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
    };*/
    /*private float[] vertexPoints = new float[]{
            -0.75f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.0f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
            0.75f, -0.5f, 0.0f
    };*/
    private float[] vertexPoints = new float[]{
            -0.5f, 0.5f, 0.0f,
            0.0f, 0.15f, 0.0f,
            0.0f, -0.4f, 0.0f,
            0.0f, 0.15f, 0.0f,
            0.5f, 0.5f, 0.0f
    };

    private static final int FLOAT_SIZE_BYTES = 4;//每个浮点型占4字节空间

    private final FloatBuffer vertexBuffer;

    public SimpleRenderer() {
        //分配内存空间,每个浮点型占4字节空间
        vertexBuffer = ByteBuffer.allocateDirect(vertexPoints.length * FLOAT_SIZE_BYTES)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        //传入指定的坐标数据
        vertexBuffer.put(vertexPoints);
        vertexBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置背景颜色
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        //编译
        final int vertexShaderId = ShaderHelper.compileVertexShader(vertexShader);
        final int fragShaderId = ShaderHelper.compileFragShader(fragmentShader);

        //链接程序片段
        int mProgram = ShaderHelper.linkProgram(vertexShaderId, fragShaderId);

        //在OpenGLES环境中使用程序片段
        GLES30.glUseProgram(mProgram);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {//Surface尺寸改变时调用到
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        //准备坐标数据
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer);
        //启用顶点的句柄
        GLES30.glEnableVertexAttribArray(0);

        //GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 3);
        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, 5);
        //GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 0, 3);
        GLES30.glLineWidth(10);
        //GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);//三角形

        //禁止顶点数组的句柄
        GLES30.glDisableVertexAttribArray(0);
    }
}
