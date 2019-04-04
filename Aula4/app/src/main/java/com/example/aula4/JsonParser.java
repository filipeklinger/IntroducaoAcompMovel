package com.example.aula4;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonParser {
    Context mContext;
    public JsonParser(Context context){
        mContext = context;
    }

    public String getJson(){
        String json;
        try {
            InputStream is = mContext.getAssets().open("climatempo.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e("Json Utils","Error Loading FROM Asset");
            return null;
        }
        return json;
    }

}
