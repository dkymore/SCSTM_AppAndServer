package com.dkymore.myscstm.Data;

import android.content.Context;

import com.dkymore.myscstm.MainActivity;
import com.dkymore.myscstm.UI.LoginAndLogupFragment;
import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.PersonalManager;

public class Player {
    public interface DoNext{
        void Invoke();
    }

    public final boolean isMock = false;

    public String getServerUrl(){
        return isMock ? "http://127.0.0.1" : Secert.ServerUrl;
    }

    public boolean isLogin() {
        return isLogin;
    }

    private boolean isLogin = false;

    public static Player player = new Player();

    public String PlayerToken;

    public String username;

    public int playerId;

    public RequestHelper rqh;

    public LocalDB localDB;

    Player() {
        rqh = new RequestHelper(getServerUrl());
    }

    public void Init(MainActivity main){
        localDB = new LocalDB(main.getSharedPreferences("data",Context.MODE_PRIVATE));
        GetToken(main.getApplicationContext());
    }

    public void Login(Context context,String _username,String password,DoNext doNext){
        rqh.Login(_username,password,new RequestHelper.Callback<String>(){
            @Override
            public void Success(String o) {
                username = _username;
                TokenSet(o);
                doNext.Invoke();
            }

            @Override
            public void Error() {
                CommonTools.toastMake(context,"网络错误");
            }

            @Override
            public void Failed(String message, String code) {
                CommonTools.toastMake(context,message);
            }
        });
    }

    public void Logout(Context context){
        rqh.Logout(new RequestHelper.Callback<String>() {
            @Override
            public void Success(String o) {
                TokenExpire();
            }

            @Override
            public void Error() {
                TokenExpire();
            }

            @Override
            public void Failed(String message, String code) {
                TokenExpire();
            }
        });
    }

    public void Logup(Context context,String _username,String password,String name,String phone,DoNext doNext){
        rqh.Logup(_username, password, name, phone, new RequestHelper.Callback<String>() {
            @Override
            public void Success(String o) {
                username = _username;
                TokenSet(o);
                doNext.Invoke();
            }

            @Override
            public void Error() {
                CommonTools.CommonError(context);
            }

            @Override
            public void Failed(String message, String code) {
                CommonTools.toastMake(context,message);
            }
        });
    }

    public void getID(Context context,String username){
        rqh.getID(username, new RequestHelper.Callback<Integer>() {
            @Override
            public void Success(Integer o) {
                playerId = o;
                return;
            }

            @Override
            public void Error() {
                CommonTools.CommonError(context);
            }

            @Override
            public void Failed(String message, String code) {
                CommonTools.toastMake(context,message);
            }
        });
    }

    public void GetToken(Context context){
        String token = localDB.Get("Token");
        if(token.isEmpty()){TokenExpire();return; }

        rqh.IsUser("", new RequestHelper.Callback() {
            @Override
            public void Success(Object o) {
            }

            @Override
            public void Error() {
            }

            @Override
            public void Failed(String message, String code) {
            }
        });
    }

    public void TokenSet(String Token){
        isLogin = true;
        PlayerToken = Token;
        localDB.Set("Token",Token);
    }

    public void TokenExpire(){
        isLogin = false;
        PlayerToken = "";
        PersonalManager.instance.switchType(PersonalManager.Type.NoUser);
    }


}
