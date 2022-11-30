package com.dkymore.framework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkymore.framework.BehaviourFramewrok.BehaviourFragment;
import com.dkymore.framework.BehaviourFramewrok.BehaviourMainActivity;
import com.dkymore.framework.databinding.FragmentShow2Binding;

public class fragment_show2 extends BehaviourFragment {

    FragmentShow2Binding binding;

    @Override
    public String getID() {
        return "Show2";
    }

    @Override
    public String getHeader(){return "首页";}

    @Override
    public boolean isAddTohistory() {
        return true;
    }

    @Override
    public View onMyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShow2Binding.inflate(inflater, container, false);

        binding.button1.setOnClickListener(view -> {
            BehaviourMainActivity.pageManager.changeToPage("Show1");
        });
        binding.button2.setOnClickListener(view -> {
            BehaviourMainActivity.pageManager.changeToPage("Show3");
        });

        View root = binding.getRoot();
        return root;
    }
}
