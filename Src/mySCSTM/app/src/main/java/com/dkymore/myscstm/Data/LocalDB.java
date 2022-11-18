package com.dkymore.myscstm.Data;

import android.content.SharedPreferences;

public class LocalDB {
    private SharedPreferences sharedPreferences;

    public LocalDB(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public String Get(String key){
        return sharedPreferences.getString(key,"");
    }

    public void Set(String key,String value){
        sharedPreferences.edit().putString(key, value).commit();
    }
}
