package com.dkymore.framework.BehaviourFramewrok;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.dkymore.framework.R;

public abstract class BehaviourMainActivity extends AppCompatActivity {
    private static final int UpdateTimeDelay = 3000;

    public static BehaviourPageManager pageManager;

    private Handler updateHandler;
    private Runnable updateThread = new Runnable() {
        @Override
        public void run() {
            CallUpdate();
            updateHandler.postDelayed(updateThread, UpdateTimeDelay);
        }
    };

    protected abstract void behaviour_onCreate(Bundle savedInstanceState,BehaviourPageManager pageManager);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageManager = new BehaviourPageManager();
        behaviour_onCreate(savedInstanceState,pageManager);
    }

    @Override
    protected void onDestroy() {
        updateHandler.removeCallbacks(updateThread);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(pageManager.OnBack()){}else{super.onBackPressed();}
    }

    private void CallUpdate(){
        BehaviourFragment f = pageManager.getCurrFragment();
        if(f!=null){
            f.Update();
        }
    }
}
