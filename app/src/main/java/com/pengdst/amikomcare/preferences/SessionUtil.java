package com.pengdst.amikomcare.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionUtil {
    Context context;
    SharedPreferences sharedPreferences;

    public SessionUtil(Context context){
        this.context = context;
        String prefName = this.context.getPackageName().toString();

        sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public static SessionUtil init(Context context){
        return new SessionUtil(context);
    }

    public SessionUtil set(String key, String value){
        sharedPreferences.edit().putString(key, value).apply();
        return this;
    }

    public SessionUtil set(String key, int value){
        sharedPreferences.edit().putInt(key, value).apply();
        return this;
    }

    public SessionUtil set(String key, boolean value){
        sharedPreferences.edit().putBoolean(key, value).apply();
        return this;
    }

    public SessionUtil set(String key, float value){
        sharedPreferences.edit().putFloat(key, value).apply();
        return this;
    }

    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public void register(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregister(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
