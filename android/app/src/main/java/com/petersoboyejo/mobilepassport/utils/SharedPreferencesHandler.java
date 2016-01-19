package com.petersoboyejo.mobilepassport.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.petersoboyejo.mobilepassport.R;

public class SharedPreferencesHandler {
    public static void wipeSharedPreferences(Context ctx) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (getBoolean(ctx, "rememberMe")) {
            editor.putString("user", getString(ctx, "user"));
            editor.putString("password", getString(ctx, "password"));
        }
        editor.clear();
        editor.apply();
    }

    public static void writeInt(Context ctx, String key, int valor) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, valor);
        editor.apply();
    }

    public static void writeString(Context ctx, String key, String valor) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, valor);
        editor.apply();
    }

    public static void writeBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context ctx, String key) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static String getString(Context ctx, String key) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }
}