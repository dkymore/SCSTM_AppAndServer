package com.dkymore.myscstm.Data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestHelper {
    OkHttpClient okHttpClient;
    private String baseUrl;

    public RequestHelper(String baseUrl){
        this.baseUrl = baseUrl;
        okHttpClient = new OkHttpClient();
    }

    String addBaseUrl(String path){
        HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl).newBuilder();
        return  httpBuilder.addPathSegment(path).build().toString();
    }

    MediaType getJSONMediaType(){
        return MediaType.parse("application/json");
    }

    Call postQuery(String url, Map<String,String> params, okhttp3.Callback callback){
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for(Map.Entry<String, String> param : params.entrySet()) {
                httpBuilder.addQueryParameter(param.getKey(),param.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader("Authorization", "Bearer " + Player.player.PlayerToken)
                .post(RequestBody.create(null, new byte[0]))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    Call postJSON(String url, String json, okhttp3.Callback callback){
        RequestBody body = RequestBody.create(getJSONMediaType(), json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + Player.player.PlayerToken)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    class Parser<T>{
        DataPack<T> parseJson(String json){
            return (new Gson()).fromJson(json,new TypeToken<DataPack<T>>(){}.getType());
        }

        okhttp3.Callback callbackPacker(Callback<T> callback){
            return new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    callback.Error();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i("Request On",response.body().string());
                    DataPack<T> dp = parseJson(response.body().string());
                    if(dp.code.equals("20000")){
                        callback.Success(dp.data);
                    }else{
                        OnCodeError(dp.code);
                        callback.Failed(dp.message,dp.code);
                    }
                }
            };
        }
    }



    private void OnCodeError(String code){
        switch (code){
            case "50012": // Token 过期
                Player.player.TokenExpire();
                break;
        }
    }

    public Call IsUser(String username,Callback callback){
        Map<String,String> params = new HashMap<String,String>();
        params.put("username",username);
        return postQuery(addBaseUrl("/user/isUser"), params,new Parser<String>().callbackPacker(callback));
    }

    public Call Login(String username,String password,Callback callback){
        return postJSON(addBaseUrl("/user/login"),(new Gson()).toJson(new UserPassPack(username,password)),new Parser<String>().callbackPacker(callback));
    }

    public Call Logout(Callback callback){
        return postQuery(addBaseUrl("/user/logout"),null,new Parser<String>().callbackPacker(callback));
    }

    public Call Logup(String username,String passwd,String name,String phone,Callback callback){
        return postJSON(addBaseUrl("/user/logup"),(new Gson()).toJson(new UserDataPack(username,passwd,name,phone)),new Parser<String>().callbackPacker(callback));
    }

    public Call getID(String username,Callback callback){
        Map<String,String> params = new HashMap<String,String>();
        params.put("username",username);
        return postQuery(addBaseUrl("/user/getId"),params,new Parser<Integer>().callbackPacker(callback));
    }

    public Call getReserves(String username,Callback callback){
        Map<String,String> params = new HashMap<String,String>();
        params.put("username",username);
        return postQuery(addBaseUrl("/api/getReserves"),params,new Parser<List<reserveData>>().callbackPacker(callback));
    }

    public Call setReserves(int userid,String time,String person , String part,String desp,Callback callback){
        return postJSON(addBaseUrl("/api/setReserves"),(new Gson()).toJson(new reserveData(userid,time,person,part,desp)),new Parser<String>().callbackPacker(callback));
    }

    public Call cancelReserves(int id,Callback callback){
        Map<String,String> params = new HashMap<String,String>();
        params.put("id",""+id);
        return postQuery(addBaseUrl("/api/cancelReserves"),params,new Parser<String>().callbackPacker(callback));
    }

    public interface Callback<T>{
        void Success(T t);
        void Error();
        void Failed(String message,String code);
    }

    public class DataPack<T>{
        public String code;
        public String message;
        public T data;
    }

    public class UserPassPack{
        public String username;
        public String password;

        public UserPassPack(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public class UserDataPack{
        public String username;
        public String passwd;
        public String name;
        public String phone;

        public UserDataPack(String username, String passwd, String name, String phone) {
            this.username = username;
            this.passwd = passwd;
            this.name = name;
            this.phone = phone;
        }
    }

    public class reserveData{
        public int id;
        public int userid;
        public String time;
        public String person;
        public String part;
        public String desp;

        public reserveData(int id, int userid, String time, String person, String part, String desp) {
            this.id = id;
            this.userid = userid;
            this.time = time;
            this.person = person;
            this.part = part;
            this.desp = desp;
        }

        public reserveData(int userid, String time, String person, String part, String desp) {
            this.id = 0;
            this.userid = userid;
            this.time = time;
            this.person = person;
            this.part = part;
            this.desp = desp;
        }
    }

}
