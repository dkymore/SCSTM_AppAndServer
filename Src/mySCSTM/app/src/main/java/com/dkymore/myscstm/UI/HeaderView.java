package com.dkymore.myscstm.UI;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.dkymore.myscstm.MainActivity;
import com.dkymore.myscstm.R;
import com.dkymore.myscstm.Utils.CommonTools;
import com.sxu.shadowdrawable.ShadowDrawable;

public class HeaderView {
    ImageView LogoF;
    ImageView LogoS;
    TextView Textc;
    boolean isLong;

    public static final HeaderView instance = new HeaderView();
    private MainActivity main;

    public void Init(MainActivity main){
        this.main = main;
        main.findViewById(R.id.header_ic_menu).setOnClickListener((View v)->{
            SliderNavView.instance.Open();
        });

        LogoF = main.findViewById(R.id.header_main_logo);
        LogoS = main.findViewById(R.id.header_main_logo2);
        Textc = main.findViewById(R.id.header_text);

        HeaderView.instance.shortHeader();
    }

    public void shortHeader(){
        LogoF.setVisibility(View.VISIBLE);
        LogoS.setVisibility(View.INVISIBLE);
        Textc.setVisibility(View.INVISIBLE);
        ShadowDrawable.setShadowDrawable(
                main.findViewById(R.id.header),
                Color.parseColor("#FFFFFF"),
                CommonTools.dpToPx(main.getApplicationContext(),0),
                Color.parseColor("#66000000"),
                CommonTools.dpToPx(main.getApplicationContext(),10),
                4, 0);
        isLong = false;
    }

    public void longHeader(String headerText){
        LogoF.setVisibility(View.INVISIBLE);
        LogoS.setVisibility(View.VISIBLE);
        Textc.setVisibility(View.VISIBLE);
        Textc.setText(headerText);
        ShadowDrawable.setShadowDrawable(
                main.findViewById(R.id.header),
                Color.parseColor("#FFFFFF"),
                CommonTools.dpToPx(main.getApplicationContext(),0),
                Color.parseColor("#66000000"),
                CommonTools.dpToPx(main.getApplicationContext(),0),
                4, 0);
        isLong = true;
    }


}
