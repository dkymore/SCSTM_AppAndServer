package com.dkymore.myscstm.Utils;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dkymore.myscstm.MainActivity;
import com.dkymore.myscstm.R;
import com.dkymore.myscstm.UI.SliderNavView;

public class PersonalManager {

    LinearLayout logoutButton;
    LinearLayout infoBox;
    TextView infoBoxText;
    LinearLayout infoBoxButton;
    TextView infoBoxButtonText;
    RecyclerView reserveList;

    public enum Type{
        Loading,
        NoUser,
        NoReserve,
        FullData
    }

    public static final PersonalManager instance = new PersonalManager();

    public void Init(MainActivity main){

        logoutButton        = main.findViewById(R.id.slider_personal_logout);
        infoBox             = main.findViewById(R.id.slider_personal_infobox);
        infoBoxText         = main.findViewById(R.id.slider_personal_infobox_text);
        infoBoxButton       = main.findViewById(R.id.slider_personal_infobox_button);
        infoBoxButtonText   = main.findViewById(R.id.slider_personal_infobox_button_text);
        reserveList         = main.findViewById(R.id.slider_personal_list);

        switchType(Type.NoUser);
    }

    public void switchType(Type type){
        switch (type){
            case Loading:
                logoutButton.setVisibility(View.INVISIBLE);
                infoBox.setVisibility(View.VISIBLE);
                infoBoxButton.setVisibility(View.INVISIBLE);
                infoBoxText.setText("Loading");
                break;
            case NoUser:
                logoutButton.setVisibility(View.INVISIBLE);
                infoBox.setVisibility(View.VISIBLE);
                infoBoxButton.setVisibility(View.VISIBLE);
                infoBoxText.setText("请登录");
                infoBoxButtonText.setText("登录/注册");
                infoBoxButton.setOnClickListener(view -> {
                    PageManager.instance.changeToPage("Login");
                });
                break;
            case NoReserve:
                logoutButton.setVisibility(View.VISIBLE);
                infoBox.setVisibility(View.VISIBLE);
                infoBoxButton.setVisibility(View.VISIBLE);
                infoBoxText.setText("您还没有预约");
                infoBoxButtonText.setText("预约");
                infoBoxButton.setOnClickListener(view -> {});
                logoutButton.setOnClickListener(view -> {});
                break;
            case FullData:
                logoutButton.setVisibility(View.VISIBLE);
                infoBox.setVisibility(View.INVISIBLE);
                logoutButton.setOnClickListener(view -> {});

                break;
        }
    }


}
