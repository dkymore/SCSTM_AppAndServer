package com.dkymore.myscstm.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dkymore.myscstm.MainActivity;
import com.dkymore.myscstm.R;
import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.DataProvider;
import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.Utils.SCSTMTimeManager;
import com.dkymore.myscstm.databinding.FragmentMainPageBinding;
import com.sxu.shadowdrawable.ShadowDrawable;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainFragment extends MyFragment {

    private FragmentMainPageBinding binding;
    private String[] TimeboxInfos = new String[]{"开放中","接近闭馆时间","闭馆时间","休息日"};

    @Override
    public String getID() {
        return "Main";
    }
    @Override
    public String getHeader(){return "首页";}

    @Override
    public boolean isAddTohistory() {
        return true;
    }

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                               ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainPageBinding.inflate(inflater, container, false);

        binding.mainFuncsBox1.setOnClickListener(view -> PageManager.instance.changeToPage("Reserve"));
        binding.mainFuncsBox2.setOnClickListener(view -> PageManager.instance.changeToPage("Walkthrough"));
        binding.mainFuncsBox3.setOnClickListener(view -> PageManager.instance.changeToPage("Showup"));
        ShadowDrawable.setShadowDrawable(binding.mainTimebox,CommonTools.dpToPx(getContext(),4), Color.parseColor("#66000000"),CommonTools.dpToPx(getContext(),0),0,0);

        binding.mainNewsButton.setOnClickListener(view -> {
            PageManager.instance.changeToPage("News");
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.mainNewsList.setLayoutManager(layoutManager);
        binding.mainNewsList.setAdapter(new RecyclerView.Adapter() {
            class ViewHolder extends RecyclerView.ViewHolder{
                ConstraintLayout layout;
                TextView title;
                TextView context;
                TextView Time;

                public ViewHolder(@NonNull View view) {
                    super(view);
                    layout = (ConstraintLayout)view.findViewById(R.id.news_item);
                    title = (TextView)view.findViewById(R.id.news_item_title);
                    context = (TextView)view.findViewById(R.id.news_item_context);
                    Time = (TextView)view.findViewById(R.id.news_item_time);
                }
            }
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
                ViewHolder viewHolder = new ViewHolder(view);
                return viewHolder;
            }
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                try {
                    ViewHolder h = (ViewHolder)holder;
                    List<DataProvider.newsDetail> newsData = DataProvider.getNewsData();
                    h.layout.setOnClickListener(view -> {
                        PageManager.instance.changeToPage("NewDetail",position+"");
                    });
                    h.title.setText(CommonTools.limitLen(newsData.get(position).title,20));
                    h.context.setText(CommonTools.limitLen(newsData.get(position).context,10));
                    h.Time.setText(newsData.get(position).time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public int getItemCount() {
                try {
                    return DataProvider.getNewsData().size();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        binding.mainBanner.setAdapter(new RecyclerView.Adapter() {
            class ViewHolder extends RecyclerView.ViewHolder{
                public ImageView im;
                public ViewHolder(ImageView im){
                    super(im);
                    this.im = im;
                }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ImageView im = new ImageView(getContext());
                im.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return new ViewHolder(im);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ViewHolder h  = (ViewHolder) holder;
                switch (position){
                    case 0:
                        h.im.setImageResource(R.drawable.banner1);
                        break;
                    case 1:
                        h.im.setImageResource(R.drawable.banner2);
                        break;
                    case 2:
                        h.im.setImageResource(R.drawable.banner3);
                        break;
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void Start(){
        Update();
    }

    @Override
    public void Update(){
        binding.mainTimeboxCurrTime.setText(SCSTMTimeManager.getFormatTime());
        try {
            binding.mainTimeboxInfo.setText(TimeboxInfos[SCSTMTimeManager.IsdayOpen().ordinal()]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.mainBanner.setCurrentItem((binding.mainBanner.getCurrentItem()+1)%3);

    }

}
