package com.dkymore.myscstm.UI.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.dkymore.myscstm.Utils.CommonTools;
import com.sxu.shadowdrawable.ShadowDrawable;

import java.util.ArrayList;

public class Chooser5 {
    public interface Callback{
        void Invoke(int part);
    }

    private Context context;
    private Callback callback;
    private ArrayList<TextView> comps;
    public int currentChoose;


    public Chooser5(Context context, Activity activity ,String prefix ,CommonTools.findViewHelper findView,Callback callback){
        this.context = context;
        this.callback = callback;

        comps = new ArrayList<>();
        for(int i=1;i<6;i++){
            comps.add((TextView) findView.Invoke(CommonTools.getIDbyName(activity,prefix+i)));
        }
        for(int i=0;i<5;i++){
            TextView t = comps.get(i);
            ShadowDrawable.setShadowDrawable(t, Color.parseColor("#FFFFFFFF"),
                    CommonTools.dpToPx(context,5),
                    Color.parseColor("#992979FF"),
                    CommonTools.dpToPx(context,0),
                    0, 0);
            t.setTextColor(Color.parseColor("#000000"));
            t.setOnClickListener(view -> {
                ClearChoose();
                ShadowDrawable.setShadowDrawable(t, Color.parseColor("#4DB1E9"),
                        CommonTools.dpToPx(context,5),
                        Color.parseColor("#992979FF"),
                        CommonTools.dpToPx(context,0),
                        0, 0);
                t.setTextColor(Color.parseColor("#FFFFFF"));
                currentChoose = Integer.parseInt(t.getText().toString());
                callback.Invoke(currentChoose);
            });
        }

    }

    private void ClearChoose(){
        for(int i=0;i<5;i++){
            TextView t = comps.get(i);
            t.setTextColor(Color.parseColor("#000000"));
            ShadowDrawable.setShadowDrawable(t, Color.parseColor("#FFFFFFFF"),
                    CommonTools.dpToPx(context,5),
                    Color.parseColor("#992979FF"),
                    CommonTools.dpToPx(context,0),
                    0, 0);
        }
    }


}
