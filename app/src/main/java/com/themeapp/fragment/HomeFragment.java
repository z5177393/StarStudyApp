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
import com.themeapp.dbdao.JavaDatabase;
import com.themeapp.dbdata.TaskDb;
import com.themeapp.utils.Constant;
import com.themeapp.utils.VolleyUtils;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRvData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmenHome = inflater.inflate(R.layout.fragment_home, null);
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
        List<TaskDb> taskFromSync = JavaDatabase.instance().raskDbDao().getTaskFromSync();
        if (null != taskFromSync && taskFromSync.size() > 0) {
            initRvView(taskFromSync);
            return;
        }
        String addUrl = "&start_date=2003-10-08&end_date=2003-10-18";
        Toast.makeText(getContext(), "loading.....", Toast.LENGTH_SHORT).show();
        VolleyUtils.getInstance().getMessage(Constant.basrurl + addUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
//                Log.e("onResponse", s.toString());
                Gson gson = new Gson();
                final List<RocketBean> list = gson.fromJson(s, new TypeToken<List<RocketBean>>() {
                }.getType());

//                List<TaskDb> taskDbList = new ArrayList<TaskDb>();


                new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < list.size(); i++) {
                            TaskDb taskDb = null;
                            if (i == .0) {
                                taskDb = new TaskDb(list.get(i).getDate(), list.get(i).getExplanation(), list.get(i).getTitle(), list.get(i).getUrl(), "1");
                            } else {
                                taskDb = new TaskDb(list.get(i).getDate(), list.get(i).getExplanation(), list.get(i).getTitle(), list.get(i).getUrl(), "0");
                            }

//                            taskDbList.add(taskDb);
                            JavaDatabase.instance().raskDbDao().insertAll(taskDb);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<TaskDb> taskFromSync = JavaDatabase.instance().raskDbDao().getTaskFromSync();
                                    initRvView(taskFromSync);
                                }
                            });
                        }
                    }
                }.start();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.e("onResponse", volleyError.toString());
                Toast.makeText(getContext(), "net erroy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRvView(final List<TaskDb> taskFromSync) {
        HomeRVAdapter homeRVAdapter = new HomeRVAdapter(getContext(), taskFromSync);
        mRvData.setAdapter(homeRVAdapter);
        homeRVAdapter.setOnItemClickListener(new HomeRVAdapter.OnChildClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                switch (view.getId()){
                    case R.id.rl_contentn:
                        if (taskFromSync.get(position).isstudy.equals("1")){
                            Intent intent = new Intent(getActivity(), StudyDetailActivity.class);
                            intent.putExtra("postiondata",taskFromSync.get(position));
                            startActivity(intent);
                        }else {
                            Toast.makeText(getContext(), "This is locked,please answer the question", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.iv_answer:
                        if (taskFromSync.get(position).isstudy.equals("1")){
                            Intent intent = new Intent(getActivity(), AnswerActivity.class);
                            intent.putExtra("postiondata2",taskFromSync.get(position));
                            startActivity(intent);
                        }else {
                            Toast.makeText(getContext(), "This is locked,please answer the question", Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rv_data:
//                VolleyUtils.getInstance().getMessage("https://www.baidu.com",new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String s) {
//                                Log.e("onResponse",s.toString());
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError volleyError) {
//                                Log.e("onResponse",volleyError.toString());
//                            }
//                        });

                //https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1485262785,623560723&fm=26&gp=0.jpg
//                VolleyUtils.getInstance().setImageUrl("https://apod.nasa.gov/apod/image/cobe_milkyw.gif",iv_ceshi);
//                initDataBd();

                break;
        }
    }

}
