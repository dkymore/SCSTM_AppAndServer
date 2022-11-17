package com.dkymore.myscstm.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dkymore.myscstm.UI.Utils.Chooser2;
import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.databinding.FragmentLoginandlogupBinding;
import com.sxu.shadowdrawable.ShadowDrawable;

public class LoginAndLogupFragment extends MyFragment {

    private FragmentLoginandlogupBinding binding;

    public enum loginUIType{
        Username,
        Login,
        Reg
    }

    @Override
    public String getID() {
        return "Login";
    }

    @Override
    public String getHeader(){return "登录/注册";}

//    LoginAndLogupFragment(){
//        PageManager.instance.AddFragment(this);
//    }

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginandlogupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ShadowDrawable.setShadowDrawable(
                binding.loginButton,
                Color.parseColor("#4DB1E9"),
                CommonTools.dpToPx(getContext(),10),
                Color.parseColor("#992979FF"),
                CommonTools.dpToPx(getContext(),6),
                0, 0
        );

        switchType(loginUIType.Username);
        return root;
    }

    public void switchType(loginUIType type){
        ConstraintLayout.LayoutParams lp =  (ConstraintLayout.LayoutParams) binding.loginButton.getLayoutParams();
        switch (type){
            case Username:
                binding.loginPasswd.setVisibility(View.INVISIBLE);
                binding.loginConfimpasswp.setVisibility(View.INVISIBLE);
                binding.loginId.setVisibility(View.INVISIBLE);
                binding.loginPhonenum.setVisibility(View.INVISIBLE);
                binding.loginSex.setVisibility(View.INVISIBLE);

                lp.setMargins(0,CommonTools.dpToPx(getContext(),28),0,0);
                binding.loginButton.setLayoutParams(lp);
                binding.loginButton.setText("下一步");
                binding.loginButton.setOnClickListener(view -> {});
                break;
            case Login:
                binding.loginPasswd.setVisibility(View.VISIBLE);
                binding.loginConfimpasswp.setVisibility(View.INVISIBLE);
                binding.loginId.setVisibility(View.INVISIBLE);
                binding.loginPhonenum.setVisibility(View.INVISIBLE);
                binding.loginSex.setVisibility(View.INVISIBLE);

                lp.setMargins(0,CommonTools.dpToPx(getContext(),92),0,0);
                binding.loginButton.setLayoutParams(lp);
                binding.loginButton.setText("登录");
                binding.loginButton.setOnClickListener(view -> {

                });
                binding.loginUsername.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                break;
            case Reg:
                binding.loginPasswd.setVisibility(View.VISIBLE);
                binding.loginConfimpasswp.setVisibility(View.VISIBLE);
                binding.loginId.setVisibility(View.VISIBLE);
                binding.loginPhonenum.setVisibility(View.VISIBLE);
                binding.loginSex.setVisibility(View.VISIBLE);
                new Chooser2(getContext(),binding.loginSexBoy,binding.loginSexGirl,(isLeft)->{});

                lp.setMargins(0,CommonTools.dpToPx(getContext(),400),0,0);
                binding.loginButton.setLayoutParams(lp);
                binding.loginButton.setText("注册");
                binding.loginButton.setOnClickListener(view -> {

                });
                binding.loginUsername.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                break;
        }
    }

     void LogNextStep(){

    }

     void LogIn(){

    }

     void LogUp(){

    }

}
