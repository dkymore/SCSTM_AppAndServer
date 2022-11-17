package com.dkymore.myscstm.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dkymore.myscstm.R;
import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.DataProvider;
import com.dkymore.myscstm.Utils.MyFragment;
import com.dkymore.myscstm.Utils.PageManager;
import com.dkymore.myscstm.databinding.FragmentNewslistPageBinding;
import com.sxu.shadowdrawable.ShadowDrawable;

import java.io.IOException;
import java.util.List;

public class NewslistFragment extends MyFragment {

    private FragmentNewslistPageBinding binding;

    @Override
    public String getID() {
        return "News";
    }
    @Override
    public String getHeader(){return "新闻列表";}

    @Override
    public boolean isAddTohistory() {
        return true;
    }

    @Override
    public View onMyCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewslistPageBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.newsList.setLayoutManager(layoutManager);
        binding.newsList.setAdapter(new RecyclerView.Adapter() {
            class ViewHolder extends RecyclerView.ViewHolder{
                ConstraintLayout layout;
                TextView title;
                TextView time;

                public ViewHolder(@NonNull View view) {
                    super(view);
                    title = (TextView)view.findViewById(R.id.newitem_title);
                    time = (TextView)view.findViewById(R.id.newitem_time);
                    layout = (ConstraintLayout) view.findViewById(R.id.newitem);
                }
            }
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newcard,parent,false);
                ViewHolder viewHolder = new ViewHolder(view);
                ShadowDrawable.setShadowDrawable(view, Color.parseColor("#FFFFFF"), CommonTools.dpToPx(getContext(),0),
                        Color.parseColor("#66000000"), CommonTools.dpToPx(getContext(),4), 0, 0);
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
                    h.time.setText(newsData.get(position).time);
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

        View root = binding.getRoot();
        return root;
    }
}
