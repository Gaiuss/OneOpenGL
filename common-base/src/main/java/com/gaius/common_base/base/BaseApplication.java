package com.gaius.common_base.base;

import android.app.Application;

import com.gaius.common_base.core.AppCore;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCore.getInstance().init(this);
    }
}
