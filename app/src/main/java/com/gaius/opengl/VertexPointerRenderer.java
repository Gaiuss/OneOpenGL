package com.gaius.opengl;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VertexPointerRenderer implements GLSurfaceView.Renderer {

    private final FloatBuffer vertexFloatBuffer;

    /**
     * 点的坐标
     */
    private float[] vertexPoints = new float[]{
            //前两个为坐标,后三个为颜色
            0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
            0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 1.0f, 1.0f, 1.0f,
            -0.5f, 0.5f, 1.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
            //两个点的顶点属性
            0.0f, 0.25f, 0.5f, 0.5f, 0.5f,
            0.0f, -0.25f, 0.5f, 0.5f, 0.5f,
    };

    public VertexPointerRenderer() {
        //分配内存空间,每个浮点型占4字节空间
        vertexFloatBuffer = ByteBuffer.allocateDirect(vertexPoints.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        //传入指定的坐标数据
        vertexFloatBuffer.put(vertexPoints);
        vertexFloatBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
