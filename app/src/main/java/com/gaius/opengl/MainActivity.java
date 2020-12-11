package com.gaius.opengl;

import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gaius.common_base.base.AbsBaseActivity;

public class MainActivity extends AbsBaseActivity {

    private GLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        mGlSurfaceView = new GLSurfaceView(getApplicationContext());
        setContentView(mGlSurfaceView);
        mGlSurfaceView.setEGLContextClientVersion(3);
        GLSurfaceView.Renderer renderer = new ColorRenderer(Color.DKGRAY);
        //GLSurfaceView.Renderer renderer = new SimpleRenderer();
        mGlSurfaceView.setRenderer(renderer);
    }
}