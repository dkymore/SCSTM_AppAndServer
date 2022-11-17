package com.dkymore.myscstm.Utils;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.dkymore.myscstm.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.res.AssetManager;

public class DataProvider {
    private static MainActivity main;
    private static String showupJson;
    private static String newsJson;
    public static List<ShowupInnerData> showUpdata = null;
    public static List<newsDetail> newsData = null;

    public class newsDetail{
        public String title;//title
        public String context;
        public String time;
    }

    public class ShowupInnerData {
        public String name;
        public String image;
        public ArrayList<ShowupDetailData> items;
    }

    public class ShowupDetailData{
        public String name;
        public String image;
        public String desp;
    }

    public static void Init(MainActivity _main){
        main = _main;
    }

    private static String Read(MainActivity main,String filename) throws IOException {
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(main.getAssets().open(filename), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        //包装字符流,将字符流放入缓存里
        BufferedReader br = new BufferedReader(isr);
        String line;
        //StringBuilder和StringBuffer功能类似,存储字符串
        StringBuilder builder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            //append 被选元素的结尾(仍然在内部)插入指定内容,缓存的内容依次存放到builder中
            builder.append(line);
        }
        br.close();
        isr.close();
        return builder.toString();
    }

    public static List<ShowupInnerData> getShowupData() throws IOException {
        if(showUpdata != null){return showUpdata;}

        showupJson = Read(main,"items.json");
        Gson gson = new Gson();
        showUpdata = gson.fromJson(showupJson, new TypeToken<List<ShowupInnerData>>() {}.getType());
        return showUpdata;
    }

    public static List<newsDetail> getNewsData() throws IOException {
        if(newsData!=null){return newsData;}

        newsJson = Read(main,"news.json");
        Gson gson = new Gson();
        newsData = gson.fromJson(newsJson,new TypeToken<List<newsDetail>>(){}.getType());
        return newsData;
    }
}
