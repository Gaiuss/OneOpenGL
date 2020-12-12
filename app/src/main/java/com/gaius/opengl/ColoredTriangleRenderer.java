package com.gaius.opengl;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ColoredTriangleRenderer implements GLSurfaceView.Renderer {

    private String vertexShader = "#version 300 es \n"
            + "layout (location = 0) in vec4 vPosition;\n"
            + "layout (location = 1) in vec4 aColor;\n"
            + "out vec4 vColor;\n"
            + "void main() { \n"
            + "gl_Position = vPosition;\n"
            + "gl_PointSize = 10.0;\n"
            + "vColor = aColor;\n"
            + "}\n";

    private String fragShader = "#version 300 es \n"
            + "precision mediump float;\n"
            + "in vec4 vColor;\n"
            + "out vec4 fragColor;\n"
            + "void main() { \n"
            + "fragColor = vColor;\n"
            + "}\n";

    private float color[] = {
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    /**
     * 点的坐标
     */
    private float[] vertexPoints = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    private final FloatBuffer vertexBuffer;

    private final FloatBuffer colorBuffer;

    public ColoredTriangleRenderer() {
        //分配内存空间,每个浮点型占4字节空间
        vertexBuffer = ByteBuffer.allocateDirect(vertexPoints.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        //传入指定的坐标数据
        vertexBuffer.put(vertexPoints);
        vertexBuffer.position(0);

        colorBuffer = ByteBuffer.allocateDirect(color.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置背景色
        GLES30.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

        //编译
        final int vertexShaderId = ShaderHelper.compileVertexShader(vertexShader);
        final int fragShaderId = ShaderHelper.compileFragShader(fragShader);
        //链接程序片段
        int mProgram = ShaderHelper.linkProgram(vertexShaderId, fragShaderId);
        //在OpenGL ES环境中使用程序片段
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

        //绘制三角形颜色
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1, 4, GLES30.GL_FLOAT, false, 0, colorBuffer);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        //禁止顶点数据的句柄
        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }
}
