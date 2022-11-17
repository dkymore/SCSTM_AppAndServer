package com.dkymore.myscstm.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;

public class CommonTools {
    public interface findViewHelper{
        View Invoke(int id);
    }

    public static int dpToPx(Context context, int dp) {
        return dp * (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int pxToDp(Context context, int px) {
        return px / (context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float spToPx(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int pxToSp(Context context, float px) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    public static String limitLen(String input,int maxEnd){
        int len = input.length();
        if(len>maxEnd) {
            return input.substring(0, maxEnd).replace("\n","");
        }else{
            return input;
        }
    }

    public static int getIDbyName(Activity self,String name){
        return self.getResources().getIdentifier(name,"id",self.getApplicationContext().getPackageName());
    }

    public static Bitmap getAssetsBitmap(Context context, String path) {
        AssetManager am = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = am.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }



}
