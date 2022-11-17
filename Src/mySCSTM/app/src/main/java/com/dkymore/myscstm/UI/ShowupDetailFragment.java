package com.dkymore.myscstm.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.DataProvider;
import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.databinding.FragmentShowupDetailpageBinding;

import java.io.IOException;

public class ShowupDetailFragment extends MyFragment {
    private FragmentShowupDetailpageBinding binding;

    @Override
    public String getID() {
        return "ShowupDetail";
    }
    @Override
    public String getHeader(){return "展示详情";}

    @Override
    public boolean isAddTohistory() {
        return true;
    }

//    ShowupDetailFragment(){
//        PageManager.instance.AddFragment(this);
//    }

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShowupDetailpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void Start(String intent){
        int show = Integer.parseInt(intent.split("_")[0]);
        int de = Integer.parseInt(intent.split("_")[1]);
        try {
            binding.showupDetailContext.setText(DataProvider.getShowupData().get(show).items.get(de).desp);
            binding.showupDetailImage.setImageBitmap(CommonTools.getAssetsBitmap(
                    getContext(),
                    DataProvider.getShowupData().get(show).items.get(de).image
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
