package org.firstinspires.ftc.teamcode;

import android.app.Application;
import android.content.Context;

/**
 * Created by rishabhbector on 10/7/17.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}