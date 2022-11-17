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
import com.dkymore.myscstm.databinding.FragmentShowupPageBinding;
import com.sxu.shadowdrawable.ShadowDrawable;

import java.io.IOException;

public class ShowupFragment extends MyFragment {

    private FragmentShowupPageBinding binding;

    @Override
    public String getID() {
        return "Showup";
    }
    @Override
    public String getHeader(){return "展示";}

    @Override
    public boolean isAddTohistory() {
        return true;
    }

//    ShowupFragment(){
//        PageManager.instance.AddFragment(this);
//    }

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShowupPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.showupList.removeAllViews();
        ShadowDrawable.setShadowDrawable(binding.showupBackbtn, Color.parseColor("#4DB1E9"),
                CommonTools.dpToPx(getContext(),6),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(getContext(),2),
                0, 0);
        binding.showupBackbtn.setOnClickListener(view -> {
            PageManager.instance.changeToPage("Main");
        });
        try {
            (new TableBuilder(getContext(),binding.showupList)).build(DataProvider.getShowupData().size(),(i,vg)->{
                try {
                    DataProvider.ShowupInnerData sid = DataProvider.getShowupData().get(i);
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.item_showup,vg,false);
                    ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(CommonTools.dpToPx(getContext(),200),CommonTools.dpToPx(getContext(),200));
                    lp.setMargins(20,20,20,20);
                    ((ConstraintLayout)v.findViewById(R.id.layout_showup)).setLayoutParams(lp);
                    ((TextView)v.findViewById(R.id.layout_text)).setText(sid.name);
                    ShadowDrawable.setShadowDrawable(((TextView)v.findViewById(R.id.layout_text)),Color.parseColor("#4DB1E9"),
                            CommonTools.dpToPx(getContext(),2),
                            Color.parseColor("#992979FF"),
                            CommonTools.dpToPx(getContext(),2),
                            0, 0);
                    ((ImageView)v.findViewById(R.id.layout_image)).setImageBitmap(CommonTools.getAssetsBitmap(getContext(),sid.image));
                    v.setOnClickListener(view -> {
                        PageManager.instance.changeToPage("ShowupInner",""+i);
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
        return root;
    }
}
