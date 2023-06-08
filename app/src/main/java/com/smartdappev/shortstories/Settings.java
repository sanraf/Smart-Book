package com.smartdappev.shortstories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class Settings extends SyncNetwork {
    public static  final String PREFERENCE = "preference";
    public static  final String CUSTOM_THEME = "customTheme";
    public static  final String LIGHT_THEME = "lightTheme";
    public static  final String DARK_THEME = "darkTheme";
    public static  final String MATRIX_THEME = "matrixTheme";

    private  String customTheme;

    public String getCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(String customTheme) {
        this.customTheme = customTheme;
    }
    public static void reset(Context context){
        SharedPreferences preferences = context.getSharedPreferences(Settings.PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(Settings.DARK_THEME);
        editor.remove(Settings.LIGHT_THEME);
        editor.remove(Settings.MATRIX_THEME);
        editor.clear();
        editor.apply();
        editor.commit();
    }


}
