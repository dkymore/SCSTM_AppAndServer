package com.dkymore.myscstm.UI;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dkymore.myscstm.MainActivity;
import com.dkymore.myscstm.R;
import com.dkymore.myscstm.Utils.PageManager;

public class SliderNavView {
    DrawerLayout slider;
    boolean isOpen;

    public static final SliderNavView instance = new SliderNavView();

    public void Init(MainActivity main){
        slider = main.findViewById(R.id.main_slider);
        slider.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                isOpen = true;
                Update();

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                isOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        main.findViewById(R.id.slider_button_mainpage).setOnClickListener((view -> {
            PageManager.instance.changeToPage("Main");
        }));

        main.findViewById(R.id.slider_button_showpage).setOnClickListener(view -> {
            PageManager.instance.changeToPage("Showup");
        });

    }


    public void Open(){
        //if(isOpen){return;}
        slider.openDrawer(Gravity.LEFT);
        isOpen = true;
        Update();
    }

    public void Close(){
        //if(!isOpen){return;}
        slider.closeDrawer(Gravity.LEFT);
        isOpen = false;
    }

    public void Update(){

    }
}
