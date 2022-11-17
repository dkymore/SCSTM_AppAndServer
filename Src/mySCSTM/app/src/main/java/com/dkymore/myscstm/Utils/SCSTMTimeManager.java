package com.dkymore.myscstm.Utils;

import com.dkymore.myscstm.UI.Utils.Chooser2;
import com.dkymore.myscstm.UI.Utils.ReserveArea;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class SCSTMTimeManager {
    public static Calendar calendar = Calendar.getInstance();
    private static Date MstartTime = null;
    private static Date MEndTime = null;
    private static Date MNearEndTime = null;


    public enum scstmOpenType {
        Open,
        NearToClose,
        NotInCurrentTime,
        NotInOpenDay
    }

    public static void Init(){
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

        try {
            MstartTime = df.parse("9:00");
            MEndTime = df.parse("19:00");
            MNearEndTime = df.parse("18:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static String getFormatTime(){
        return (new SimpleDateFormat("HH:mm")).format(new Date());
    }

    public static scstmOpenType IsdayOpen() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date tm = df.parse(df.format(new Date()));
        return IsdayOpen(tm);
    }

    public static scstmOpenType IsdayOpen(Date tm) throws ParseException {
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(dayWeek<0){dayWeek=0;}

        if(dayWeek==1){
            return scstmOpenType.NotInOpenDay;
        }else if(tm.before(MstartTime)||tm.after(MEndTime)) {
            return scstmOpenType.NotInCurrentTime;
        }else if(tm.after(MEndTime)){
            return scstmOpenType.NearToClose;
        }else{
            return scstmOpenType.Open;
        }
    }

    public static scstmOpenType IsdayOpen(Date tm,boolean NotDay) throws ParseException {
        if(NotDay){
            int dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if(dayWeek<0){dayWeek=0;}

            if(dayWeek==1){
                return scstmOpenType.NotInOpenDay;
            }else{
                return scstmOpenType.Open;
            }
        }else{
            return IsdayOpen(tm);
        }
    }

    public static ArrayList<ReserveArea.ReserveInfo> getInfo() throws ParseException {
        ArrayList<ReserveArea.ReserveInfo> rs = new ArrayList<ReserveArea.ReserveInfo>();
        Calendar tc = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E");

        rs.add(new ReserveArea.ReserveInfo(
                sdf.format(tc.getTime()),
                IsdayOpen(tc.getTime()),
                (new Random()).nextInt(10000)
        ));
        tc.add(Calendar.DATE, 1);
        rs.add(new ReserveArea.ReserveInfo(
                sdf.format(tc.getTime()),
                IsdayOpen(tc.getTime(),true),
                (new Random()).nextInt(10000)
        ));
        tc.add(Calendar.DATE, 1);
        rs.add(new ReserveArea.ReserveInfo(
                sdf.format(tc.getTime()),
                IsdayOpen(tc.getTime(),true),
                (new Random()).nextInt(10000)
        ));
        tc.add(Calendar.DATE, 1);
        rs.add(new ReserveArea.ReserveInfo(
                sdf.format(tc.getTime()),
                IsdayOpen(tc.getTime(),true),
                (new Random()).nextInt(10000)
        ));
        return rs;
    }
}
