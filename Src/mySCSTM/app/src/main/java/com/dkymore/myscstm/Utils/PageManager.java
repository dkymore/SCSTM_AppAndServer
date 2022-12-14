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

    // 回调函数
    public interface PageManagerCallback{
        void invoke();
    }

    // 一些变量
    MyFragment currFragment;
    FragmentContainerView fcv;
    FragmentManager manager;
    FragmentTransaction transaction;

    // 通过这变量确认
    // 因为一次最多只会有一个在切换 就无需使用时间戳
    /**
     * 是否在切换中
     */
    boolean isChangeing = false;

    // 存储所有创建的 myfragment
    List<MyFragment> fragments = new ArrayList<>();

    // 返回值类及栈用来保存返回值
    public class History{
        public String name;
        public String Intent;

        public History(String name, String intent) {
            this.name = name;
            Intent = intent;
        }
    }
    Stack<History> history =  new Stack<>();

    // 单例模式
    public static final PageManager instance = new PageManager();


    public void Init(MainActivity main){
        fcv = main.findViewById(R.id.nav_host_fragment_content_main);
        manager = main.getSupportFragmentManager();
        DataInit();
    }

    // 一次性创建
    // 其实没有必要 可以在切换时才创建 更节约性能 减少启动时间
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

    // 切换页面主力方法
    // 使用 transaction 的 hide 和 show
    public void changeToPage(String From,String To,PageManagerCallback callback,String intent){
        if(From.isEmpty()||To.isEmpty()){Log.e("changeToPage","Args Empty");return;}
        if(isChangeing){Log.w("changeToPage","is Changing");return;}
        isChangeing = true;

        OnBeforeChangePage(From,To);

        transaction = manager.beginTransaction();
        // 可以把动画也抽离 作为一个参数
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

    // 这里方便期间就写死了启动"Main" 可以设置为参数
    // 也可以和上面的 changeToPage 合并
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

    // before 和 after change 的方法
    // 可以通过判断 From 和 To 进行一些操作
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

    // 当返回时 去返回值切换页面
    // 由 MainAciticy 调用
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
