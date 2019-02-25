package com.example.inass;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final String TAG = "Utils";


    //Функция для загрузки информации  в List
    public static List<Item> loadProfiles(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "pose.json"));
            List<Item> itemList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                Item item = gson.fromJson(array.getString(i), Item.class);
                itemList.add(item);
            }

            return itemList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Функция для загрузки инфы из json
    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is=null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG,"path "+jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}