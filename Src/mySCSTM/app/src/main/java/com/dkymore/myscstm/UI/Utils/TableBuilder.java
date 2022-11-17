package com.dkymore.myscstm.UI.Utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dkymore.myscstm.R;
import com.dkymore.myscstm.Utils.CommonTools;
import com.dkymore.myscstm.Utils.DataProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TableBuilder {
    public interface Builder{
        View Invoke(int i, ViewGroup vg);
    }

    private Context context;
    private LinearLayout layout;

    public TableBuilder(Context context, LinearLayout layout){
        this.context = context;
        this.layout = layout;
    }

    public void build(int size,Builder builder){
        LinearLayout tr = null;
        layout.setOrientation(LinearLayout.VERTICAL);

        for(int i=0;i<size;i++){
            if(i%2==0){
                if(tr!=null){
                    layout.addView(tr);
                }
                tr = new LinearLayout(context);
                tr.setOrientation(LinearLayout.HORIZONTAL);
                tr.setGravity(Gravity.CENTER);
            }
            tr.addView(builder.Invoke(i,layout));
        }
        if(tr!=null){
            layout.addView(tr);
        }


    }
}

//            LinearLayout lr = new LinearLayout(context);
//            lr.setOrientation(LinearLayout.HORIZONTAL);
//            lr.setGravity(Gravity.CENTER);
//            ImageView im = new ImageView(context);
//            im.setImageResource(R.drawable.boxback1);
//            im.setLayoutParams(new ViewGroup.LayoutParams(150,150));//CommonTools.dpToPx(context,150),CommonTools.dpToPx(context,150))
//            lr.addView(im);
////            TextView textView = new TextView(context);
////            textView.setText("good"+i);
////            lr.addView(textView);
//            layout.addView(lr);