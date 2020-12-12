package com.gaius.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.gaius.common_base.base.AbsBaseActivity;
import com.gyf.immersionbar.ImmersionBar;

public class MainActivity extends AbsBaseActivity {

    private GLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ImmersionBar.with(this).init();
        setupViews();
    }

    private void setupViews() {
        mGlSurfaceView = new GLSurfaceView(getApplicationContext());
        setContentView(mGlSurfaceView);
        mGlSurfaceView.setEGLContextClientVersion(3);
        //GLSurfaceView.Renderer renderer = new ColorRenderer(Color.DKGRAY);
        //GLSurfaceView.Renderer renderer = new SimpleRenderer();
        GLSurfaceView.Renderer renderer = new ColoredTriangleRenderer();
        mGlSurfaceView.setRenderer(renderer);
    }

}