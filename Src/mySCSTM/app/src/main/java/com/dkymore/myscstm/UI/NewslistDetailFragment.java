package com.dkymore.myscstm.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dkymore.myscstm.Utils.DataProvider;
import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.databinding.FragmentNewslistDetailpageBinding;

import java.io.IOException;
import java.util.ArrayList;

public class NewslistDetailFragment extends MyFragment {

    private FragmentNewslistDetailpageBinding binding;

    @Override
    public String getID() {
        return "NewDetail";
    }
    @Override
    public String getHeader(){return "新闻详情";}

    @Override
    public boolean isAddTohistory() {
        return true;
    }

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewslistDetailpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.newdetailHeaderBack.setOnClickListener(view -> {
            PageManager.instance.changeToPage("News");
        });
        return root;
    }

    @Override
    public void Start(String intent) {
        try {
            DataProvider.newsDetail r = DataProvider.getNewsData().get(Integer.parseInt(intent));
            binding.newdetailHeaderText.setText(r.title);
            binding.newdetailText.setText((r.context));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
