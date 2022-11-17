package com.dkymore.myscstm.UI.Utils;

import androidx.viewpager2.widget.ViewPager2;

import com.dkymore.myscstm.MainActivity;
import com.dkymore.myscstm.R;
import com.dkymore.myscstm.UI.HeaderView;

public class BannerManager {
    ViewPager2 vp;


    public static final BannerManager instance = new BannerManager();

    public void Init(MainActivity main){
        vp = main.findViewById(R.id.main_banner);


    }
}
