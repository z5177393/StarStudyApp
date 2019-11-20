package com.themeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themeapp.R;
import com.themeapp.dbdata.TaskDb;
import com.themeapp.utils.VolleyUtils;

import java.util.List;

public class HomeRVAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TaskDb> rocketbeans;

    public HomeRVAdapter(Context context, List<TaskDb> rocketbeans) {
        this.mContext = context;
        this.rocketbeans = rocketbeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_fragment_study, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        ((ItemHolder)viewHolder).tv_title.setText(rocketbeans.get(i).title);
        ((ItemHolder)viewHolder).tv_des.setText(rocketbeans.get(i).explanation);
        if (rocketbeans.get(i).isstudy.equals("1")) {
            ((ItemHolder) viewHolder).fl_lock.setVisibility(View.GONE);
        }else {
            ((ItemHolder) viewHolder).fl_lock.setVisibility(View.VISIBLE);
        }
        VolleyUtils.getInstance().setImageUrl(rocketbeans.get(i).url, ((ItemHolder)viewHolder).ivImage);

        ((ItemHolder)viewHolder).rl_contentn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(i,((ItemHolder)viewHolder).rl_contentn);
                }
            }
        });

        ((ItemHolder)viewHolder).iv_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(i,((ItemHolder)viewHolder).iv_answer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rocketbeans.size();
    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tv_title;
        private TextView tv_des;
        private FrameLayout fl_lock;
        private RelativeLayout rl_contentn;
        private ImageView iv_answer;


        public ItemHolder(final View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_des = (TextView) itemView.findViewById(R.id.tv_des);
            fl_lock = (FrameLayout) itemView.findViewById(R.id.fl_lock);
            rl_contentn = (RelativeLayout) itemView.findViewById(R.id.rl_contentn);
            iv_answer = (ImageView) itemView.findViewById(R.id.iv_answer);
        }
    }

    private OnChildClickListener onItemClickListener;//声明一下这个接口

    public void setOnItemClickListener(OnChildClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnChildClickListener {
        void onItemClick(int position,View view);
    }
}
