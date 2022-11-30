package com.dkymore.framework.BehaviourFramewrok;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BehaviourFragment extends Fragment {
    public abstract String getID();

    public String getHeader(){return "";}

    public boolean isAddTohistory(){return false;}

    public abstract View onMyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onMyCreateView(inflater,container,savedInstanceState);
    }

    public void Awake(){}

    public void Start(){}

    public void Start(String intent){Start();}

    public void Update(){}

}
