package com.example.don.testaccessibility;

import android.app.Application;

/**
 * Created by don on 2/15/17.
 */

public class MyApplication extends Application {

  public static boolean sIsRun = false;
  public static boolean sIsOn = false;

  @Override
  public void onCreate() {
    super.onCreate();
  }
}
