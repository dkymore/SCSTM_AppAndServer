package com.dkymore.myscstm.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dkymore.myscstm.Data.Player;
import com.dkymore.myscstm.Data.RequestHelper;
import com.dkymore.myscstm.UI.Utils.Chooser2;
import com.dkymore.myscstm.UI.Utils.Chooser5;
import com.dkymore.myscstm.UI.Utils.ReserveArea;
import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.Utils.PersonalManager;
import com.dkymore.myscstm.databinding.FragmentReservePageBinding;
import com.sxu.shadowdrawable.ShadowDrawable;

public class ReserveFragment extends MyFragment {

    private FragmentReservePageBinding binding;

    public enum reserveUIType{
        NoLogin,
        Normal
    }

    @Override
    public String getID() {
        return "Reserve";
    }
    @Override
    public String getHeader(){return "预约";}

    ReserveArea reserveArea;
    Chooser2 part;
    Chooser5 person;

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReservePageBinding.inflate(inflater, container, false);

        switchType(reserveUIType.NoLogin);
        View root = binding.getRoot();

        Start();
        return root;
    }

    @Override
    public void Start(){
        if(Player.player.isLogin()){
            switchType(reserveUIType.Normal);
        }else{
            switchType(reserveUIType.NoLogin);
        }
    }

    private void ReserveAreaInit(){
        reserveArea = new ReserveArea(this, (id) -> {
            return binding.getRoot().findViewById(id);
        });
    }

    public void switchType(reserveUIType type){
        switch (type){
            case NoLogin:
                binding.reserveInfobox.setVisibility(View.VISIBLE);
                binding.reserveServebox.setVisibility(View.INVISIBLE);

                ShadowDrawable.setShadowDrawable(binding.reserveInfoboxButton, Color.parseColor("#4DB1E9"),
                        CommonTools.dpToPx(getContext(),10),
                        Color.parseColor("#992979FF"),
                        CommonTools.dpToPx(getContext(),6),
                        0, 0);
                binding.reserveInfoboxButton.setOnClickListener(view -> PageManager.instance.changeToPage("Login"));
                break;
            case Normal:
                binding.reserveInfobox.setVisibility(View.INVISIBLE);
                binding.reserveServebox.setVisibility(View.VISIBLE);
                ReserveAreaInit();
                part = new Chooser2(getContext(),binding.reserveTimepart1,binding.reserveTimepart2,(isLeft)->{});
                person = new Chooser5(getContext(),getActivity(),"reserve_num",(id)->{return binding.getRoot().findViewById(id);},(i)->{});
                ShadowDrawable.setShadowDrawable(binding.reserveBtn,Color.parseColor("#4DB1E9"),
                        CommonTools.dpToPx(getContext(),10),
                        Color.parseColor("#992979FF"),
                        CommonTools.dpToPx(getContext(),6),
                        0, 0);
                binding.reserveBtn.setOnClickListener(view -> {
                    checkAndCommit();
                });
                break;
        }
    }

    private void checkAndCommit(){
        Player.player.rqh.setReserves(
                Player.player.username,
                reserveArea.CurrDateTime,
                (double) person.currentChoose,
                part.isLeft ? 0d : 1d,
                "",
                new RequestHelper.Callback<String>() {
                    @Override
                    public void Success(String o) {
                        PageManager.instance.changeToPage("Main");
                        CommonTools.toastMake(getContext(),"预约成功");
                        PersonalManager.instance.UpdateReserveList();
                    }

                    @Override
                    public void Error() {
                        CommonTools.CommonError(getContext());
                    }

                    @Override
                    public void Failed(String message, String code) {
                        CommonTools.toastMake(getContext(),message);
                    }
                }
        );
    }
}
