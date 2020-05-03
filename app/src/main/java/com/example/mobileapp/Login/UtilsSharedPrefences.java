package com.example.mobileapp.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilsSharedPrefences {

    final static String FileName = "MyFileName";

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static void SharedPrefesSAVE(Context ctx,String Email){
        SharedPreferences prefs = ctx.getSharedPreferences("EMAIL", 0);
        SharedPreferences.Editor prefEDIT = prefs.edit();
        prefEDIT.putString("EMAIL", Email);
        prefEDIT.commit();
    }

}
