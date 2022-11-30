package com.dkymore.myscstm.UI.Utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.dkymore.myscstm.R;
import com.dkymore.myscstm.Utils.CommonTools;
import com.sxu.shadowdrawable.ShadowDrawable;

public class Chooser2 {
    public interface Callback{
        void Invoke(boolean isLeft);
    }

    private Callback callback;
    private TextView comp1;
    private TextView comp2;
    private Context context;
    public boolean isLeft;

    public Chooser2(Context context, TextView comp1, TextView comp2,Callback callback){
        this.comp1 = comp1;
        this.comp2 = comp2;
        this.callback = callback;
        this.context = context;

        comp1.setOnClickListener(view -> {
            activeLeft();
            isLeft = true;
            callback.Invoke(true);
        });

        comp2.setOnClickListener(view -> {
            activeRight();
            isLeft = false;
            callback.Invoke(false);
        });

        activeNo();
    }

    private void activeLeft(){
        comp1.setTextColor(Color.parseColor("#FFFFFF"));
        comp2.setTextColor(Color.parseColor("#000000"));

        ShadowDrawable.setShadowDrawable(
                comp1,
                Color.parseColor("#4DB1E9"),
                CommonTools.dpToPx(context,5),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(context,0),
                0, 0
        );
        ShadowDrawable.setShadowDrawable(
                comp2,
                Color.parseColor("#FFFFFFFF"),
                CommonTools.dpToPx(context,5),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(context,0),
                0, 0
        );
    }

    private void activeRight(){
        comp1.setTextColor(Color.parseColor("#000000"));
        comp2.setTextColor(Color.parseColor("#FFFFFF"));

        ShadowDrawable.setShadowDrawable(
                comp1,
                Color.parseColor("#FFFFFFFF"),
                CommonTools.dpToPx(context,5),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(context,0),
                0, 0
        );
        ShadowDrawable.setShadowDrawable(
                comp2,
                Color.parseColor("#4DB1E9"),
                CommonTools.dpToPx(context,5),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(context,0),
                0, 0
        );
    }

    private void activeNo(){
        comp1.setTextColor(Color.parseColor("#000000"));
        comp2.setTextColor(Color.parseColor("#000000"));

        ShadowDrawable.setShadowDrawable(
                comp1,
                Color.parseColor("#FFFFFFFF"),
                CommonTools.dpToPx(context,5),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(context,0),
                0, 0
        );
        ShadowDrawable.setShadowDrawable(
                comp2,
                Color.parseColor("#FFFFFFFF"),
                CommonTools.dpToPx(context,5),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(context,0),
                0, 0
        );
    }

    public void reset(){
        activeNo();
    }
}
