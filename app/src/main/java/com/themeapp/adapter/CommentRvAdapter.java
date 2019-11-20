package com.themeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themeapp.R;
import com.themeapp.dbdata.CommentDb;
import com.themeapp.dbdata.TaskDb;
import com.themeapp.utils.VolleyUtils;

import java.util.List;

public class CommentRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CommentDb> commentDb;

    public CommentRvAdapter(Context context, List<CommentDb> commentDb) {
        this.mContext = context;
        this.commentDb = commentDb;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment_rv, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ((ItemHolder)viewHolder).tv_item_comment.setText(commentDb.get(i).comment);
        ((ItemHolder)viewHolder).tv_comment_date.setText(commentDb.get(i).time);
    }

    @Override
    public int getItemCount() {
        return commentDb.size();
    }

    private class ItemHolder extends RecyclerView.ViewHolder {


        private TextView tv_item_comment;
        private TextView tv_comment_date;



        public ItemHolder(final View itemView) {
            super(itemView);

            tv_item_comment = (TextView) itemView.findViewById(R.id.tv_item_comment);
            tv_comment_date = (TextView) itemView.findViewById(R.id.tv_comment_date);

        }
    }

    private OnItemClickListener onItemClickListener;//声明一下这个接口

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{

        void onItemClick(int position);

    }
}
