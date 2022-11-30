package com.dkymore.framework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dkymore.framework.BehaviourFramewrok.BehaviourMainActivity;
import com.dkymore.framework.BehaviourFramewrok.BehaviourPageManager;

public class MainActivity extends BehaviourMainActivity {

    @Override
    protected void behaviour_onCreate(Bundle savedInstanceState,BehaviourPageManager pageManager) {
        setContentView(R.layout.activity_main);
        pageManager.Init(this,findViewById(R.id.nav_host_fragment_content_main));
        pageManager.startToPage("Show1");
    }

}