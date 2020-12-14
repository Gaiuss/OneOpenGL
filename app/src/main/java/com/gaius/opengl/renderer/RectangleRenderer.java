package com.gaius.opengl.renderer;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.gaius.opengl.utils.ShaderHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RectangleRenderer implements GLSurfaceView.Renderer {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;

    private static final int BYTES_PER_FLOAT = 4;

    private static final int STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private String vertexShader = "#version 300 es \n"
            + "layout (location = 0) in vec4 vPosition;\n"
            + "layout (location = 1) in vec4 aColor;\n"
            + "out vec4 vColor;\n"
            + "void main() { \n"
            + "gl_Position = vPosition;\n"
            + "gl_PointSize = 10.0\n"
            + "vColor = aColor;\n"
            + "}\n";

    private String fragShader = "#version 300 es \n"
            + "precision mediump float;\n"
            + "in vec4 vColor;\n"
            + "out vec4 fragColor;\n"
            + "void main() { \n"
            + "fragColor = vColor;\n"
            + "}\n";

    private float[] vertexPoints = new float[]{
            //前两个是坐标,后三个是颜色RGB
            0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
            0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 1.0f, 1.0f, 1.0f,
            -0.5f, 0.5f, 1.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, 1.0f, 1.0f, 1.0f,

            0.0f, 0.25f, 0.5f, 0.5f, 0.5f,
            0.0f, -0.25f, 0.5f, 0.5f, 0.5f
    };

    private final FloatBuffer vertexBuffer;

    public RectangleRenderer() {
        //分配内存空间,每个浮点型占4字节空间
        vertexBuffer = ByteBuffer.allocateDirect(vertexPoints.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        vertexBuffer.put(vertexPoints);//传入指定的坐标数据
        vertexBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        //设置背景颜色
        GLES30.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

        //编译
        final int vertexShaderId = ShaderHelper.compileVertexShader(vertexShader);
        final int fragShaderId = ShaderHelper.compileFragShader(fragShader);
        //链接程序片段
        int mProgram = ShaderHelper.linkProgram(vertexShaderId, fragShaderId);
        //在OpenGL ES 环境中使用程序片段
        GLES30.glUseProgram(mProgram);

        int aPositionLocation = GLES30.glGetAttribLocation(mProgram, "vPosition");
        int aColorLocation = GLES30.glGetAttribLocation(mProgram, "aColor");

        vertexBuffer.position(0);
        //获取顶点数组 (POSITION_COMPONENT_COUNT = 2)
        GLES30.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES30.GL_FLOAT,
                false, STRIDE, vertexBuffer);

        GLES30.glEnableVertexAttribArray(aPositionLocation);

        vertexBuffer.position(POSITION_COMPONENT_COUNT);

        //颜色属性分量的数量 COLOR_COMPONENT_COUNT = 3
        GLES30.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES30.GL_FLOAT,
                false, STRIDE, vertexBuffer);

        GLES30.glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
