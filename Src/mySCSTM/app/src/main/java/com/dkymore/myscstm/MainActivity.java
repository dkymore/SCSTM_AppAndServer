package com.dkymore.myscstm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;

import com.dkymore.myscstm.UI.HeaderView;
import com.dkymore.myscstm.UI.SliderNavView;
import com.dkymore.myscstm.Utils.DataProvider;
import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.Utils.PersonalManager;
import com.dkymore.myscstm.Utils.SCSTMTimeManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Handler updateHandler;
    Runnable updateThread = new Runnable() {
        @Override
        public void run() {
            CallUpdate();
            updateHandler.postDelayed(updateThread, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataProvider.Init(this);

        PageManager.instance.Init(this);
        PageManager.instance.startToPage();
        HeaderView.instance.Init(this);
        SliderNavView.instance.Init(this);
        PersonalManager.instance.Init(this);
        SCSTMTimeManager.Init();

        updateHandler = new Handler();
        updateHandler.removeCallbacks(updateThread);
        updateHandler.post(updateThread);

    }

    @Override
    protected void onDestroy() {
        updateHandler.removeCallbacks(updateThread);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(PageManager.instance.OnBack()){}else{super.onBackPressed();}
    }

    private void CallUpdate(){
        MyFragment f = PageManager.instance.getCurrFragment();
        if(f!=null){
            f.Update();
        }
    }
}



//        try {
//            DataProvider.getData(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }