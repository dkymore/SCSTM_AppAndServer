package com.dkymore.framework.BehaviourFramewrok;

import android.util.Log;

import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dkymore.framework.fragment_Show1;
import com.dkymore.framework.fragment_show2;
import com.dkymore.framework.fragment_show3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BehaviourPageManager {
    public interface Callback{
        void Invoke();
    }

    private BehaviourFragment currFragment;
    private String currFragmentID;
    private FragmentContainerView fcv;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private boolean isChangeing = false;

    List<BehaviourFragment> fragments = new ArrayList<>();

    public class History{
        public String name;
        public String Intent;

        public History(String name, String intent) {
            this.name = name;
            Intent = intent;
        }
    }
    Stack<History> history =  new Stack<>();

    public void Init(BehaviourMainActivity main,FragmentContainerView fcv){
        this.fcv = fcv;
        fragmentManager = main.getSupportFragmentManager();
        DataInit();
    }

    void DataInit(){
        history.clear();
        fragments.clear();
        fragments.add(new fragment_Show1());
        fragments.add(new fragment_show2());
        fragments.add(new fragment_show3());
    }

    public void changeToPage(String To){
        changeToPage(To,()->{});
    }

    public void changeToPage(String To,Callback callback){
        String From = ((BehaviourFragment) currFragment).getID();
        changeToPage(From,To,callback,"");
    }

    public void changeToPage(String To,String intent){
        String From = ((BehaviourFragment) currFragment).getID();
        changeToPage(From,To,()->{},intent);
    }

    public void changeToPage(String From,String To,Callback callback,String intent){
        if(From.isEmpty()||To.isEmpty()){
            Log.e("changeToPage","Args Empty");return;}
        if(isChangeing){Log.w("changeToPage","is Changing");return;}
        isChangeing = true;

        OnBeforeChangePage(From,To);

        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.hide(currFragment);
        BehaviourFragment toFragement = getFragment(To);
        if(toFragement.isAdded()){
            transaction.show(toFragement);
        }else{
            transaction.add(fcv.getId(),toFragement);
        }

        transaction.runOnCommit(()->{
            isChangeing = false;
            currFragment = toFragement;
            if(currFragment.isAddTohistory()){history.push(new History(To,intent));}
            OnAfterChangePage(From,To);

            callback.Invoke();
            currFragment.Start(intent);
        }).commit();
    }

    public void startToPage(String startPageID){
        BehaviourFragment toFragement = getFragment(startPageID);
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(fcv.getId(),toFragement);
        transaction.runOnCommit(()->{
            currFragment = toFragement;
            if(currFragment.isAddTohistory()){history.push(new History("Main",""));}
            OnAfterChangePage("","Main");
        }).commit();
    }


    public BehaviourFragment getCurrFragment(){
        return currFragment;
    }

    public BehaviourFragment getFragment(String id){
        for (BehaviourFragment e: fragments
        ) {
            if(e.getID().equals(id)){
                return e;
            }
        }
        return null;
    }

    private void OnBeforeChangePage(String From,String To){

    }

    private void OnAfterChangePage(String From,String To){
        if(To.equals("Main")){
            // Add your code here
        }else{

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
