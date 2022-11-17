package com.dkymore.myscstm.Utils;


import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import com.dkymore.myscstm.MainActivity;
import com.dkymore.myscstm.R;
import com.dkymore.myscstm.UI.HeaderView;
import com.dkymore.myscstm.UI.LoginAndLogupFragment;
import com.dkymore.myscstm.UI.MainFragment;
import com.dkymore.myscstm.UI.NewslistDetailFragment;
import com.dkymore.myscstm.UI.NewslistFragment;
import com.dkymore.myscstm.UI.ReserveFragment;
import com.dkymore.myscstm.UI.ShowupDetailFragment;
import com.dkymore.myscstm.UI.ShowupFragment;
import com.dkymore.myscstm.UI.ShowupInnerFragment;
import com.dkymore.myscstm.UI.SliderNavView;
import com.dkymore.myscstm.UI.WalkthroughFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PageManager {
    public interface PageManagerCallback{
        void invoke();
    }

    MyFragment currFragment;
    FragmentContainerView fcv;
    FragmentManager manager;
    FragmentTransaction transaction;
    boolean isChangeing = false;

    List<MyFragment> fragments = new ArrayList<>();

    public class History{
        public String name;
        public String Intent;

        public History(String name, String intent) {
            this.name = name;
            Intent = intent;
        }
    }
    Stack<History> history =  new Stack<>();

    public static final PageManager instance = new PageManager();

    public void Init(MainActivity main){
        fcv = main.findViewById(R.id.nav_host_fragment_content_main);
        manager = main.getSupportFragmentManager();
        DataInit();
    }

    void DataInit(){
        history.clear();
        fragments.clear();
        fragments.add(new LoginAndLogupFragment());
        fragments.add(new MainFragment());
        fragments.add(new NewslistFragment());
        fragments.add(new NewslistDetailFragment());
        fragments.add(new ReserveFragment());
        fragments.add(new ShowupFragment());
        fragments.add(new ShowupInnerFragment());
        fragments.add(new ShowupDetailFragment());
        fragments.add(new WalkthroughFragment());
    }

    public void changeToPage(String To){
        changeToPage(To,()->{});
    }

    public void changeToPage(String To,PageManagerCallback callback){
        String From = ((MyFragment) currFragment).getID();
        changeToPage(From,To,callback,"");
    }

    public void changeToPage(String To,String intent){
        String From = ((MyFragment) currFragment).getID();
        changeToPage(From,To,()->{},intent);
    }

    public void changeToPage(String From,String To,PageManagerCallback callback,String intent){
        if(From.isEmpty()||To.isEmpty()){Log.e("changeToPage","Args Empty");return;}
        if(isChangeing){Log.w("changeToPage","is Changing");return;}
        isChangeing = true;

        OnBeforeChangePage(From,To);

        transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.hide(currFragment);
        MyFragment toFragement = getFragment(To);
        if(toFragement.isAdded()){
            transaction.show(toFragement);
        }else{
            transaction.add(R.id.nav_host_fragment_content_main,toFragement);
        }

        transaction.runOnCommit(()->{
            isChangeing = false;
            currFragment = toFragement;
            if(currFragment.isAddTohistory()){history.push(new History(To,intent));}
            OnAfterChangePage(From,To);

            callback.invoke();
            currFragment.Start(intent);
        }).commit();
    }

    public void startToPage(){
        MyFragment toFragement = getFragment("Main");
        transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(R.id.nav_host_fragment_content_main,toFragement);
        transaction.runOnCommit(()->{
            currFragment = toFragement;
            if(currFragment.isAddTohistory()){history.push(new History("Main",""));}

            OnAfterChangePage("","Main");
        }).commit();
    }


    public MyFragment getCurrFragment(){
        return currFragment;
    }

    public MyFragment getFragment(String id){
        for (MyFragment e: fragments
             ) {
            if(e.getID().equals(id)){
                return e;
            }
        }
        return null;
    }

    private void OnBeforeChangePage(String From,String To){
        SliderNavView.instance.Close();

    }

    private void OnAfterChangePage(String From,String To){
        if(To.equals("Main")){
            HeaderView.instance.shortHeader();
        }else{
            HeaderView.instance.longHeader(currFragment.getHeader());
        }
    }

    public boolean OnBack(){
        if(history.size()>1){
            history.pop();
            History h = history.pop();
            changeToPage(h.name,h.Intent);
            return true;
        }
        return false;
    }

}

//    public void changeToPage(String To){
//        currFragment = fcv.getFragment();
//        String From = ((MyFragment) currFragment).getID();
//        if(From.equals(To)){
//            Log.e("changeToPage","From.equals(To)"+To);
//            return;
//        }
//        String link =  To + From;
//        if(pagesLinks.containsKey(link)){
//            changeToPage(pagesLinks.get(link).link,From,To);
//        }else{
//            Log.e("changeToPage","no find link:"+link);
//            return;
//        }
//
//    }
//
//    public void changeToPage(int linkId,String From,String To){
//        currFragment = fcv.getFragment();
//        changeToPage(linkId,currFragment,From,To);
//    }
//
//    public void changeToPage(int linkId, Fragment self,String From,String To){
//        OnBeforeChangePage(From,To);
//        NavHostFragment.findNavController(self).navigate(linkId);
//
//    }
//
//    public void changeToPage(int linkId, Fragment self,@Nullable PageManagerCallback callback){
//        NavHostFragment.findNavController(self).navigate(linkId);
//        if(callback != null){
//            callback.invoke();
//        }
//    }
