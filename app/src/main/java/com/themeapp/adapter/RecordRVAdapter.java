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

public class RecordRVAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TaskDb> rocketbeans;

    public RecordRVAdapter(Context context, List<TaskDb> rocketbeans) {
        this.mContext = context;
        this.rocketbeans = rocketbeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_record_fragment, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        ((ItemHolder)viewHolder).tv_title.setText(rocketbeans.get(i).title);
        ((ItemHolder)viewHolder).tv_des.setText(rocketbeans.get(i).explanation);

        VolleyUtils.getInstance().setImageUrl(rocketbeans.get(i).url, ((ItemHolder)viewHolder).ivImage);

        ((ItemHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(i);
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




        public ItemHolder(final View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_des = (TextView) itemView.findViewById(R.id.tv_des);


        }
    }

    private OnChildClickListener onItemClickListener;//声明一下这个接口

    public void setOnItemClickListener(OnChildClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnChildClickListener {
        void onItemClick(int position);
    }
}
