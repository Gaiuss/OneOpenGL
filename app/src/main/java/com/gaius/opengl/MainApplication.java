package com.gaius.opengl;

import android.app.Application;

import com.gaius.common_base.core.AppCore;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCore.getInstance().init(this);
    }
}
