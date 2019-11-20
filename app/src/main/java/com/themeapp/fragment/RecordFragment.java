package com.themeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.themeapp.Bean.RocketBean;
import com.themeapp.R;
import com.themeapp.activity.AnswerActivity;
import com.themeapp.activity.StudyDetailActivity;
import com.themeapp.adapter.HomeRVAdapter;
import com.themeapp.adapter.RecordRVAdapter;
import com.themeapp.dbdao.JavaDatabase;
import com.themeapp.dbdata.TaskDb;
import com.themeapp.utils.Constant;
import com.themeapp.utils.VolleyUtils;

import java.util.List;

public class RecordFragment extends Fragment {

    private RecyclerView mRvData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmenHome = inflater.inflate(R.layout.framment_record, null);
        mRvData = fragmenHome.findViewById(R.id.rv_data);
        initData();
        return fragmenHome;
    }

    @Override
    public void onResume() {

        super.onResume();
        initData();
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRvData.setLayoutManager(linearLayoutManager);
        List<TaskDb> taskFromSync = JavaDatabase.instance().raskDbDao().getvaluebytisstudy();
        if (null != taskFromSync && taskFromSync.size() > 0) {
            taskFromSync.remove(taskFromSync.size() -1);
            Log.e("获取子isstrudy=1", "长度: ..." + taskFromSync.size());
            initRvView(taskFromSync);
            return;
        }else {
            return;
        }
    }

    private void initRvView(final List<TaskDb> taskFromSync) {
        RecordRVAdapter homeRVAdapter = new RecordRVAdapter(getContext(), taskFromSync);
        mRvData.setAdapter(homeRVAdapter);
        homeRVAdapter.setOnItemClickListener(new RecordRVAdapter.OnChildClickListener() {
            @Override
            public void onItemClick(int position) {
                if (taskFromSync.get(position).isstudy.equals("1")){
                    Intent intent = new Intent(getActivity(), StudyDetailActivity.class);
                    intent.putExtra("postiondata",taskFromSync.get(position));
                    startActivity(intent);
                }
            }
        });
    }

}
