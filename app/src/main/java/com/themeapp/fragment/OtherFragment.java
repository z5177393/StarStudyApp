package com.themeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.themeapp.Bean.SearchBean;
import com.themeapp.R;
import com.themeapp.utils.Constant;
import com.themeapp.utils.VolleyUtils;

public class OtherFragment extends Fragment {


    private SearchView searchveiew;
    private ImageView iv_sear_img;
    private TextView tv_sear_title;
    private TextView tv_sear_des;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmenHome = inflater.inflate(R.layout.fragment_other, null);
        searchveiew = fragmenHome.findViewById(R.id.searchveiew);
        iv_sear_img = fragmenHome.findViewById(R.id.iv_sear_img);
        tv_sear_title = fragmenHome.findViewById(R.id.tv_sear_title);
        tv_sear_des = fragmenHome.findViewById(R.id.tv_sear_des);



        searchveiew.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getdetail(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return fragmenHome;
    }

    private void getdetail(String searchdate){
        VolleyUtils.getInstance().getMessage(Constant.basrurl + "&date="+searchdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Date must be between Jun 16, 1995 and Nov 19, 2019.
//                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    Gson gson = new Gson();
                    SearchBean searchBean = gson.fromJson(s, SearchBean.class);
                    tv_sear_title.setText(searchBean.getTitle());
                    tv_sear_des.setText(searchBean.getExplanation());
                    VolleyUtils.getInstance().setImageUrl(searchBean.getUrl(),iv_sear_img);
                }catch (Exception e){
                    Toast.makeText(getContext(), "warning! please enter a valid date in standard format", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "warning! please enter a valid date in standard format", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
