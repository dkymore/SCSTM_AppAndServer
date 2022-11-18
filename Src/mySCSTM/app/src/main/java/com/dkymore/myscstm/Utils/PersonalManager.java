package com.dkymore.myscstm.Utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dkymore.myscstm.Data.Player;
import com.dkymore.myscstm.Data.RequestHelper;
import com.dkymore.myscstm.MainActivity;
import com.dkymore.myscstm.R;
import com.dkymore.myscstm.UI.SliderNavView;
import com.sxu.shadowdrawable.ShadowDrawable;

import java.util.List;

public class PersonalManager {

    LinearLayout logoutButton;
    LinearLayout infoBox;
    TextView infoBoxText;
    LinearLayout infoBoxButton;
    TextView infoBoxButtonText;
    RecyclerView reserveList;
    private MainActivity main;

    public enum Type{
        Loading,
        NoUser,
        NoReserve,
        FullDataLoading,
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
        this.main = main;

        switchType(Type.Loading);
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
                logoutButton.setOnClickListener(view -> Player.player.Logout(main));
                infoBox.setVisibility(View.VISIBLE);
                infoBoxButton.setVisibility(View.VISIBLE);
                infoBoxText.setText("您还没有预约");
                infoBoxButtonText.setText("预约");
                infoBoxButton.setOnClickListener(view -> {PageManager.instance.changeToPage("Reserve");});
                logoutButton.setOnClickListener(view -> {Player.player.Logout(main.getApplicationContext());});
                break;
            case FullDataLoading:
                switchType(Type.Loading);
                UpdateReserveList();
            case FullData:
                logoutButton.setVisibility(View.VISIBLE);
                logoutButton.setOnClickListener(view -> Player.player.Logout(main.getApplicationContext()));
                UpdateReserveList();
                break;
        }
    }

    private void UpdateReserveList(){
        Player.player.rqh.getReserves(Player.player.username, new RequestHelper.Callback<List<RequestHelper.reserveData>>() {
            @Override
            public void Success(List<RequestHelper.reserveData> o) {
                _UpdateReserveList(o);
            }

            @Override
            public void Error() {
                CommonTools.CommonError(main.getApplicationContext());
            }

            @Override
            public void Failed(String message, String code) {
                CommonTools.toastMake(main.getApplicationContext(),message);
            }
        });
    }

    private void _UpdateReserveList(List<RequestHelper.reserveData> datas){
        reserveList.setLayoutManager(new LinearLayoutManager(main.getApplicationContext()));
        reserveList.setAdapter(new RecyclerView.Adapter() {
            class ViewHolder extends RecyclerView.ViewHolder{
                public ConstraintLayout layout;
                public TextView time;
                public TextView part;
                public ViewHolder(@NonNull View view) {
                    super(view);
                    layout = (ConstraintLayout)view.findViewById(R.id.item_reserve);
                    time = (TextView)view.findViewById(R.id.item_reserve_time);
                    part = (TextView)view.findViewById(R.id.item_reserve_part);
                }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserve,parent,false);
                ViewHolder viewHolder = new ViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ViewHolder h = (ViewHolder) holder;
                RequestHelper.reserveData data = datas.get(position);

                ShadowDrawable.setShadowDrawable(h.layout,CommonTools.dpToPx(main.getApplicationContext(),4), Color.parseColor("#66000000"),CommonTools.dpToPx(main.getApplicationContext(),0),0,0);
                h.layout.setOnLongClickListener(view -> {
                    new AlertDialog.Builder(main.getApplicationContext())
                            .setMessage("确认取消该预约？")
                            .setPositiveButton("确认",(dialogInterface,i)->{
                                CancelReserve(main.getApplicationContext(),data.id);
                                dialogInterface.dismiss();
                            })
                            .create().show();
                    return true;
                });
                h.time.setText(data.time);
                h.part.setText(CommonTools.buildPartInfo(data.part,data.person));
            }

            @Override
            public int getItemCount() {
                return datas.size();
            }
        });
    }

    public void CancelReserve(Context context, int id){
        Player.player.rqh.cancelReserves(id, new RequestHelper.Callback() {
            @Override
            public void Success(Object o) {
                CommonTools.toastMake(context,"成功取消");
                UpdateReserveList();
            }

            @Override
            public void Error() {
                CommonTools.CommonError(context);
            }

            @Override
            public void Failed(String message, String code) {
                CommonTools.toastMake(context,message);
            }
        });
    }
}
