package com.dkymore.myscstm.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dkymore.myscstm.R;
import com.dkymore.myscstm.UI.Utils.TableBuilder;
import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.DataProvider;
import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.databinding.FragmentShowupInnerpageBinding;
import com.sxu.shadowdrawable.ShadowDrawable;

import java.io.IOException;

public class ShowupInnerFragment extends MyFragment {

    private FragmentShowupInnerpageBinding binding;

    @Override
    public String getID() {
        return "ShowupInner";
    }
    @Override
    public String getHeader(){return "展示";}

    @Override
    public boolean isAddTohistory() {
        return true;
    }

//    ShowupInnerFragment(){
//        PageManager.instance.AddFragment(this);
//    }

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShowupInnerpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ShadowDrawable.setShadowDrawable(binding.showupinnerBackbtn, Color.parseColor("#4DB1E9"),
                CommonTools.dpToPx(getContext(),6),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(getContext(),2),
                0, 0);
        binding.showupinnerBackbtn.setOnClickListener(view -> {
            PageManager.instance.changeToPage("Showup");
        });
        return root;
    }

    @Override
    public void Start(String intent){
        binding.showupinnerLayout.removeAllViews();

        if(intent.isEmpty()){return;}
        int show = Integer.parseInt(intent);

        try {
            (new TableBuilder(getContext(),binding.showupinnerLayout)).build(DataProvider.getShowupData().get(show).items.size(),(i, vg)->{
                try {
                    DataProvider.ShowupDetailData sid = DataProvider.getShowupData().get(show).items.get(i);
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.item_showup,vg,false);
                    ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(CommonTools.dpToPx(getContext(),200),CommonTools.dpToPx(getContext(),200));
                    lp.setMargins(20,20,20,20);
                    ((ConstraintLayout)v.findViewById(R.id.layout_showup)).setLayoutParams(lp);
                    ((TextView)v.findViewById(R.id.layout_text)).setText(sid.name);
                    ShadowDrawable.setShadowDrawable(((TextView)v.findViewById(R.id.layout_text)), Color.parseColor("#4DB1E9"),
                            CommonTools.dpToPx(getContext(),2),
                            Color.parseColor("#992979FF"),
                            CommonTools.dpToPx(getContext(),2),
                            0, 0);
                    ((ImageView)v.findViewById(R.id.layout_image)).setImageBitmap(CommonTools.getAssetsBitmap(getContext(),sid.image));
                    v.setOnClickListener(view -> {
                        PageManager.instance.changeToPage("ShowupDetail",""+show+"_"+i);
                    });
                    return v;
                } catch (IOException e) {
                    e.printStackTrace();
                    return new View(getContext());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
