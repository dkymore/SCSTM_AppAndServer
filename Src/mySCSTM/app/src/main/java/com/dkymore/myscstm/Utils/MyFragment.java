package com.dkymore.myscstm.Utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class MyFragment extends Fragment {

    /**
     * 返回类的ID 用于在 pagemanager 识别类
     * @return
     */
    public abstract String getID();

    /**
     * 用于在 设置在 headerview 中显示的字符串
     * @return
     */
    public String getHeader(){return "";}

    /**
     * 是否添加到历史记录
     * @return
     */
    public boolean isAddTohistory(){return false;}

    /**
     * 实际继承的 onCreate
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract View onMyCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onMyCreateView(inflater,container,savedInstanceState);
    }

    // 添加的 behaviour
    // 当 pagemanger 切换时 调用对应方法
    // 方便处理数据
    // Update 方法 在 MainActiviy 中以线程调用 默认每3秒调用一次

    public void Awake(){}

    public void Start(){}

    public void Start(String intent){Start();}

    public void Update(){}

}
