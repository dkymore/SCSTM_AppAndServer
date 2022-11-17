package com.dkymore.myscstm.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.databinding.FragmentWalkthroughPageBinding;

public class WalkthroughFragment extends MyFragment {

    private FragmentWalkthroughPageBinding binding;

    @Override
    public String getID() {
        return "Walkthrough";
    }
    @Override
    public String getHeader(){return "攻略";}

    @Override
    public boolean isAddTohistory() {
        return true;
    }

//    WalkthroughFragment(){
//        PageManager.instance.AddFragment(this);
//    }

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWalkthroughPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}
