package com.dkymore.myscstm.UI.Utils;

import static com.dkymore.myscstm.Utils.SCSTMTimeManager.getInfo;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dkymore.myscstm.R;
import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.SCSTMTimeManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ReserveArea {

    public static class ReserveInfo{
        public String DataTime;
        public SCSTMTimeManager.scstmOpenType type;
        public int remainTickets;

        public ReserveInfo(String dataTime, SCSTMTimeManager.scstmOpenType type, int remainTickets) {
            DataTime = dataTime;
            this.type = type;
            this.remainTickets = remainTickets;
        }
    }

    private class DPack{
        public CardView c;
        public TextView d;
        public TextView i;

        public DPack(CardView c, TextView d, TextView i) {
            this.c = c;
            this.d = d;
            this.i = i;
        }
    }

    private Fragment fragment;
    private CommonTools.findViewHelper finder;
    private List<DPack> ableCards;
    private String CurrDateTime = "";

    public ReserveArea(Fragment fragment ,CommonTools.findViewHelper findView){
        this.fragment = fragment;
        this.finder = findView;
        try {
            fillData(getInfo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void fillData(ArrayList<ReserveInfo> rfs){
        ableCards = new ArrayList<>();
        int i = 1;
        for (ReserveInfo rf:rfs
             ) {                                                                                                // reserve_serve1_datetime
            TextView datetime = (TextView) finder.Invoke(CommonTools.getIDbyName(fragment.getActivity(),"reserve_serve"+i+"_datetime"));
            TextView res = (TextView) finder.Invoke(CommonTools.getIDbyName(fragment.getActivity(),"reserve_serve"+i+"_info"));
            CardView cl = (CardView) finder.Invoke(CommonTools.getIDbyName(fragment.getActivity(),"reserve_serve"+i));

            datetime.setText(rf.DataTime);
            res.setTextColor(Color.parseColor("#000000"));
            switch (rf.type){
                case Open:
                    res.setText("余票："+rf.remainTickets);
                    res.setTextColor(Color.parseColor("#000000"));
                    cl.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    ableCards.add(new DPack(cl,datetime,res));
                    cl.setOnClickListener(view -> {
                        for (DPack dp:ableCards
                             ) {
                            dp.c.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                            dp.d.setTextColor(Color.parseColor("#000000"));
                            dp.i.setTextColor(Color.parseColor("#000000"));
                        }
                        datetime.setTextColor(Color.parseColor("#FFFFFF"));
                        res.setTextColor(Color.parseColor("#FFFFFF"));
                        cl.setCardBackgroundColor(Color.parseColor("#4DB1E9"));
                        CurrDateTime = datetime.getText().toString();
                    });
                    break;
                case NearToClose:
                    res.setText("即将闭馆,停止售票");
                    res.setTextColor(Color.parseColor("#FF0000"));
                    cl.setCardBackgroundColor(Color.parseColor("#D9D9D9"));
                    break;
                case NotInCurrentTime:
                    res.setText("已停止入馆");
                    res.setTextColor(Color.parseColor("#FF0000"));
                    cl.setCardBackgroundColor(Color.parseColor("#D9D9D9"));
                    break;
                case NotInOpenDay:
                    res.setText("闭馆");
                    res.setTextColor(Color.parseColor("#FF0000"));
                    cl.setCardBackgroundColor(Color.parseColor("#D9D9D9"));
                    break;
            }
            i++;
        }

    }
}
